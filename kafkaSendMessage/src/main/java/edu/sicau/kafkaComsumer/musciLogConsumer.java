package edu.sicau.kafkaComsumer;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class musciLogConsumer {
    public static void main(String[] args) throws Exception {
        //1、创建流处理执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //TaskManager有多个TaskSlot，推荐一个cpu核心对应一个slot，env.setParallelism是使用多少个slot（参数<=slot数）。
        env.setParallelism(4);
        //2、从文件中读取数据
        //kafka配置
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","hadoop101:9092");
        properties.setProperty("auto.offset.reset","latest");
        //FlinkKafkaConsumer(topic,反序列化工具,配置properties)
        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer<String>("musicLog", new SimpleStringSchema(), properties));
        dataStream.print();
        env.execute("musicLog");
    }
}
