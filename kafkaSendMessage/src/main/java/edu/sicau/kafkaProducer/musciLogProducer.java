package edu.sicau.kafkaProducer;

import com.alibaba.fastjson.JSONObject;
import edu.sicau.beans.UserBehavior;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import com.alibaba.fastjson.JSON;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AscendingTimestampExtractor;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

public class musciLogProducer {
    public static void main(String[] args) throws Exception {
        //1、创建Kafka生产者对象

        //创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        //读取hdfs文件路径
        //sendMessageUtils(env,"2022-01-01-orderTime1.txt");
        sendMessageUtils(env,"2022-01-01-1.txt");
        //sendMessageUtils(env,"ForartistData.txt");
        sendMessageUtils(env,"2022-01-03-1.txt");
        sendMessageUtils(env,"2022-01-05-1.txt");
        sendMessageUtils(env,"2022-01-07-1.txt");
        //执行
        env.execute("HDFSSourceTest");
    }
public static void sendMessageUtils(StreamExecutionEnvironment env,String add){
    DataStream<String> hdfsSource = env.readTextFile("hdfs://hadoop101:8020/MusicSystem/morkLog/"+add);
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
                        String userId,playStartTime,playEndTime,dt,songName,songId,artistName,artistId,albumId,albumName;
                        try{
                            userId = obj.getString("userId");
                        }catch (Exception e){
                            userId = "None";
                        }
                        try {
                            playStartTime=obj.getString("playStartTime");
                        }catch (Exception e){
                            playStartTime = "None";
                        }
                        try {
                            playEndTime=obj.getString("playEndTime");
                        }catch (Exception e){
                            playEndTime = "None";
                            return null;
                        }
                        try {
                            dt=obj.getString("dt");
                        }catch (Exception e){
                            dt = "None";
                        }
                        try {
                            songName=JSON.parseObject(obj.getString("data")).getString("name");
                        }catch (Exception e){
                            songName= "None";
                        }
                        try {
                            songId=JSON.parseObject(obj.getString("data")).getString("id");
                        }catch (Exception e){
                            songId = "None";
                        }
                        try {
                            artistName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("name");
                        }catch (Exception e){
                            artistName = "None";
                        }
                        try {
                            artistId=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("id");
                        }catch (Exception e){
                            artistId = "None";
                        }
                        try {
                            albumId=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("id");
                        }catch (Exception e){
                            albumId = "None";
                        }
                        try {
                            albumName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("name");
                        }catch (Exception e){
                            albumName = "None";
                        }
                        //按属性和分割赋值给对象
                        //return  new UserBehavior(userId,playStartTime,playEndTime,dt,songId,songName,artistId,artistName,albumId,artistName);
                        return new String(userId+" "+playStartTime+" "+playEndTime+" "+dt+" "+songId+" "+songName+" "+artistId+" "+artistName+" "+albumId+" "+albumName);
                    }
            );
    //2、发送对象
    dataStream.addSink(new FlinkKafkaProducer<String>("hadoop101:9092,hadoop102:9092,hadoop103:9092","musicLog", new SimpleStringSchema()));
}
}
