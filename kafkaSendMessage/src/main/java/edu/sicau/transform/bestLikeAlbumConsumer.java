package edu.sicau.transform;

import edu.sicau.beans.bestLikeAlbum;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//最喜欢的专辑
public class bestLikeAlbumConsumer {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");
        DataStream<bestLikeAlbum> dataStream=inputStream.map(new MapFunction<String, bestLikeAlbum>() {
            @Override
            public bestLikeAlbum map(String s) throws Exception {
                String[] s1 = s.split(" ");
                return new bestLikeAlbum(Integer.valueOf(s1[0]),Integer.valueOf(s1[8]),s1[9],1);
                //return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
        KeyedStream<bestLikeAlbum, Tuple> keyedStream =dataStream.keyBy("userId").keyBy("albumId");
        DataStream<bestLikeAlbum> outputStream=keyedStream.reduce(new ReduceFunction<bestLikeAlbum>() {
            @Override
            public bestLikeAlbum reduce(bestLikeAlbum b1, bestLikeAlbum b2) throws Exception {
                b1.setCount(b1.getCount()+1);
                return b1;
            }
        });
        outputStream.print();
        env.execute();
    }
}
