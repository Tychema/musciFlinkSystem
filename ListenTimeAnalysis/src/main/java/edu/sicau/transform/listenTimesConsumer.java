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

//听歌总次数
public class listenTimesConsumer {
    public listenTimesConsumer() {
    }

    public DataStream<timeCount> listenTimeCount(DataStream<UserBehavior> inputStream){
        DataStream<timeCount> dataStream=inputStream.map(new MapFunction<UserBehavior, timeCount>() {
            @Override
            public timeCount map(UserBehavior s) throws Exception {
                return new timeCount(s.getUserId(),1);
                //return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
        //分组 可以多个字段做一个组合 所以返回为Tuple元组类型
        KeyedStream<timeCount, Tuple> keyedStream = dataStream.keyBy("userId");
        //自定义
        //聚合
        DataStream<timeCount> dt = keyedStream.reduce(new ReduceFunction<timeCount>() {
            @Override
            public timeCount reduce(timeCount  u1, timeCount u2) throws Exception {
                return new timeCount(u1.getUserId(), u1.getCount()+u2.getCount());
            }
        });
        return dt;
        //dt.print();
    }
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");

        //包装类
        DataStream<timeCount> dataStream=inputStream.map(new MapFunction<String, timeCount>() {
            @Override
            public timeCount map(String s) throws Exception {
                String[] s1 = s.split(" ");
                return new timeCount(Integer.valueOf(s1[0]),1);
                //return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
        //分组 可以多个字段做一个组合 所以返回为Tuple元组类型
        KeyedStream<timeCount, Tuple> keyedStream = dataStream.keyBy("userId");
        //自定义
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
