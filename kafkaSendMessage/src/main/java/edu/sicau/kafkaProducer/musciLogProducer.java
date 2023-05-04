package edu.sicau.kafkaProducer;

import com.alibaba.fastjson.JSONObject;
import edu.sicau.beans.UserBehavior;
import edu.sicau.utils.StringToUserbehavior;
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
        System.out.println("开始生产"+System.currentTimeMillis());
        //创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        StringToUserbehavior SToU = new StringToUserbehavior(env);
        //读取hdfs文件路径
        DataStream<String> dataStream1 = SToU.sendMessageUtils("2022-01-01-1.txt");
        //sendMessageUtils(env,"ForartistData.txt");
//        DataStream<String> dataStream2 = SToU.sendMessageUtils("2022-01-03-1.txt");
//        DataStream<String> dataStream3 = SToU.sendMessageUtils("2022-01-05-1.txt");
//        DataStream<String> dataStream4 = SToU.sendMessageUtils("2022-01-07-1.txt");
        //执行
        //2、发送对象
        dataStream1.addSink(new FlinkKafkaProducer<String>("hadoop101:9092,hadoop102:9092,hadoop103:9092", "musicLog", new SimpleStringSchema()));

//        dataStream2.addSink(new FlinkKafkaProducer<String>("hadoop101:9092,hadoop102:9092,hadoop103:9092", "musicLog", new SimpleStringSchema()));
//        dataStream3.addSink(new FlinkKafkaProducer<String>("hadoop101:9092,hadoop102:9092,hadoop103:9092", "musicLog", new SimpleStringSchema()));
//        dataStream4.addSink(new FlinkKafkaProducer<String>("hadoop101:9092,hadoop102:9092,hadoop103:9092", "musicLog", new SimpleStringSchema()));

        env.execute("HDFSSourceTest");
    }
}
