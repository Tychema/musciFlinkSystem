package edu.sicau.transform;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;

public class transformTest {
    public static void main(String[] args) throws Exception {
        //1、创建流处理执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //TaskManager有多个TaskSlot，推荐一个cpu核心对应一个slot，env.setParallelism是使用多少个slot（参数<=slot数）。
        env.setParallelism(4);
        //2、从文件中读取数据

        //FlinkKafkaConsumer(topic,反序列化工具,配置properties)
        DataStream<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test.txt");

        //1、map 把String转化为长度输出
        DataStream<Integer> outputStream1 = inputStream.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String s) throws Exception {
                return s.length();
            }
        });
        //2、flatmap()
        DataStream<String> outputStream2=inputStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                for(String ss:s.split(",")){
                    collector.collect(ss);
                }

            }
        });

        //3、filter
        DataStream<String> outputStream3 = inputStream.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String s) throws Exception {
                if(s.startsWith("{'userId': '300005475'"))
                    return true;
                else
                    return false;
            }
        });
        outputStream1.print();
        outputStream2.print();
        outputStream3.print();
        env.execute("musicLog");
    }

}
