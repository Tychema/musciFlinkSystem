package edu.sicau.kafkaProducer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AscendingTimestampExtractor;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

public class musicLogProducer2 {
    public static void main(String[] args) throws Exception {
        //1、创建Kafka生产者对象

        //创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(8);
        //读取hdfs文件路径
        DataStream<String> hdfsSource = env.readTextFile("hdfs://hadoop101:8020/MusicSystem/morkLog/2022-01-01-1.txt");
        //将hdfs文件路径打印输出
        SingleOutputStreamOperator<String> dataStream=hdfsSource
                .map(
                        line ->{
                            JSONObject obj;
                            try {
                                obj = (JSONObject) JSON.parse(line.replace("\\\"","\"").replace("\'", "\"").replace("False", "\"Flase\"").replace("None", "\"None\""));
                            }catch (Exception e){
                                return " ";
                            }
                            Integer userId,playStartTime,playEndTime,dt,songId,artistId,albumId;
                            String songName,artistName,albumName;
                            try{
                                userId = Integer.valueOf(obj.getString("userId"));
                            }catch (Exception e){
                                userId = -1;
                            }
                            try {
                                playStartTime=Integer.valueOf(obj.getString("playStartTime"));
                            }catch (Exception e){
                                playStartTime = -1;
                            }
                            try {
                                playEndTime=Integer.valueOf(obj.getString("playEndTime"));
                            }catch (Exception e){
                                playEndTime = -1;
                            }
                            try {
                                dt=Integer.valueOf(obj.getString("dt"));
                            }catch (Exception e){
                                dt = -1;
                            }
                            try {
                                songName=JSON.parseObject(obj.getString("data")).getString("name");
                            }catch (Exception e){
                                songName= "None";
                            }
                            try {
                                songId=Integer.valueOf(JSON.parseObject(obj.getString("data")).getString("id"));
                            }catch (Exception e){
                                songId = -1;
                            }
                            try {
                                artistName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("name");
                            }catch (Exception e){
                                artistName = "None";
                            }
                            try {
                                artistId=Integer.valueOf(JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("id"));
                            }catch (Exception e){
                                artistId = -1;
                            }
                            try {
                                albumId=Integer.valueOf(JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("id"));
                            }catch (Exception e){
                                albumId = -1;
                            }
                            try {
                                albumName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("name");
                            }catch (Exception e){
                                albumName = "None";
                            }
                            //按属性和分割赋值给对象
                            return new String(userId+" "+playStartTime+" "+playEndTime+" "+dt+" "+songName+" "+songId+" "+artistName+" "+artistId+" "+albumName+" "+albumId);
                        }
                );
        //2、发送对象
        dataStream.addSink(new FlinkKafkaProducer<String>("hadoop101:9092,hadoop102:9092,hadoop103:9092","musicLog", new SimpleStringSchema()));
        //执行
        env.execute("HDFSSourceTest");
    }
//    public static void main(String[] args) {
//        //1、创建Kafka生产者对象
//            // 1.1 配置Kafka
//            Properties properties = new Properties();
//            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop101:9092,hadoop102:9092");
//            //1.2指定KV的序列化类型
//            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//
//        //1.3创建对象
//            KafkaProducer<String, String> Producer = new KafkaProducer<>(properties);
//        //2、发送对象
//        for (int i = 0; i < 5; i++) {
////            Producer.send(new ProducerRecord<>("first",""+i,"logs"+i));
//        }
//
//        //3、关闭资源
//        Producer.close();
//    }
}
