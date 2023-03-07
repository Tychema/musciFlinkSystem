package edu.sicau.transform;

import edu.sicau.beans.UserBehavior;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//最喜欢的专辑
public class bestLikeAlbum {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(16);
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");
        DataStream<UserBehavior> dataStream=inputStream.map(new MapFunction<String, UserBehavior>() {
            @Override
            public UserBehavior map(String s) throws Exception {
                String[] s1 = s.split(" ");
                return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
    }
}
