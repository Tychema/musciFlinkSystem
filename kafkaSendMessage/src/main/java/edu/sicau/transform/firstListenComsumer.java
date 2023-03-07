package edu.sicau.transform;

import edu.sicau.beans.UserBehavior;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class firstListenComsumer {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");

        //包装类
        DataStream<UserBehavior> dataStream=inputStream.map(new MapFunction<String, UserBehavior>() {
            @Override
            public UserBehavior map(String s) throws Exception {
                String[] s1 = s.split(" ");
                return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
        //分组 可以多个字段做一个组合 所以返回为Tuple元组类型
        KeyedStream<UserBehavior, Tuple> keyedStream = dataStream.keyBy("userId");
        //聚合
        DataStream<UserBehavior> firstListen = keyedStream.reduce(new ReduceFunction<UserBehavior>() {
            @Override
            public UserBehavior reduce(UserBehavior  u1, UserBehavior u2) throws Exception {
                return u1.getPlayStartTime() < u2.getPlayStartTime() ? u1 : u2;
            }
        });
        firstListen.print();
//        DataStream<UserBehavior> sum = keyedStream.sum("playStartTime");
//        sum.print();
        env.execute();
    }
}
