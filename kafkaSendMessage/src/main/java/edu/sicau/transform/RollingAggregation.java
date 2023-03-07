package edu.sicau.transform;

import edu.sicau.beans.UserBehavior;
import edu.sicau.beans.timeCount;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class RollingAggregation {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");

        //包装类
        DataStream<timeCount> dataStream=inputStream.map(new MapFunction<String, timeCount>() {
            @Override
            public timeCount map(String s) throws Exception {
                String[] s1 = s.split(" ");
                return new timeCount(Integer.valueOf(s1[0]),Integer.valueOf(s1[2])-Integer.valueOf(s1[1]));
                //return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
        //分组 可以多个字段做一个组合 所以返回为Tuple元组类型
        KeyedStream<timeCount, Tuple> keyedStream = dataStream.keyBy("userId");
        //自定义
        //1、方法引用
        //KeyedStream<UserBehavior, String > keyedStream = dataStream.keyBy(UserBehavior::getUserId);
        //2、自定义传递方法
//        KeyedStream<UserBehavior, String > keyedStream = dataStream.keyBy(new KeySelector<UserBehavior, String>() {
//            @Override
//            public String getKey(UserBehavior userBehavior) throws Exception {
//                return userBehavior.getUserId();
//            }
//        });
        //聚合
        DataStream<timeCount> dt = keyedStream.reduce(new ReduceFunction<timeCount>() {
            @Override
            public timeCount reduce(timeCount  u1, timeCount u2) throws Exception {
                return new timeCount(u1.getUserId(), u1.getCount()+u2.getCount());
            }
        });
        dt.print();
//        DataStream<UserBehavior> sum = keyedStream.sum("playStartTime");
//        sum.print();
        env.execute();
    }
}
