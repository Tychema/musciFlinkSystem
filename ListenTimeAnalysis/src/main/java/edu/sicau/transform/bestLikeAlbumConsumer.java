package edu.sicau.transform;

import edu.sicau.beans.UserBehavior;
import edu.sicau.beans.bestLikeAlbum;
import edu.sicau.beans.songsHotRank;
import edu.sicau.beans.userEverySongsTimeAndTimes;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

//最喜欢的专辑
public class bestLikeAlbumConsumer {
//    public static DataStream<bestLikeAlbum> bestLikeAlbumDataWindow(DataStreamSource<String> inputStream){
//        DataStream<bestLikeAlbum> dataStream=inputStream.map(new MapFunction<String, bestLikeAlbum>() {
//            @Override
//            public bestLikeAlbum map(String s) throws Exception {
//                String[] s1 = s.split(" ");
//                return new bestLikeAlbum(Integer.valueOf(s1[0]),Integer.valueOf(s1[8]),s1[9],1);
//            }
//        }).assignTimestampsAndWatermarks(WatermarkStrategy.<songsHotRank>forBoundedOutOfOrderness(Duration.ofHours(1))
//                        .withTimestampAssigner(new SerializableTimestampAssigner<bestLikeAlbum>() {
//                            @Override
//                            public long extractTimestamp(bestLikeAlbum b1, long l) {
//                                return b1.getPlayStartTime()*1000;
//                            }
//                        }));
//        //滚动时间窗口
//        DataStream<songsHotRank> sixHourTimewindow = dataStreamWithWaterMark.keyBy(songsHotRank -> songsHotRank.getSongId())
//                .window(TumblingEventTimeWindows.of(Time.hours(6)))
//                .reduce(new ReduceFunction<songsHotRank>() {
//                    @Override
//                    public songsHotRank reduce(songsHotRank e1, songsHotRank e2) throws Exception {
//                        e1.setSongsCount(e1.getSongsCount()+1);
//                        e1.setTimeCount(e1.getTimeCount()+e2.getTimeCount());
//                        return e1;
//                    }
//                });
//        return ;
//    }

    public static DataStream<bestLikeAlbum> bestLikeAlbumStreamOperator(DataStream<UserBehavior> inputStream){
        DataStream<bestLikeAlbum> dataStream=inputStream.map(new MapFunction<UserBehavior, bestLikeAlbum>() {
            @Override
            public bestLikeAlbum map(UserBehavior u1) throws Exception {
                return new bestLikeAlbum(u1.getUserId(),u1.getAlbumId(),u1.getAlbumName(),1);
                }
        });
        DataStream<bestLikeAlbum> outputStream =dataStream.keyBy(new KeySelector<bestLikeAlbum, Tuple2<Integer,Integer>>() {
            @Override
            public Tuple2<Integer,Integer> getKey(bestLikeAlbum bestLikeAlbum) throws Exception {
                return new Tuple2<Integer,Integer>(bestLikeAlbum.getUserId(),bestLikeAlbum.getAlbumId());
            }
        }).reduce(new ReduceFunction<bestLikeAlbum>() {
            @Override
            public bestLikeAlbum reduce(bestLikeAlbum b1, bestLikeAlbum b2) throws Exception {
                b1.setCount(b1.getCount()+1);
                return b1;
            }
        }).keyBy("userId").reduce(new ReduceFunction<bestLikeAlbum>() {
            @Override
            public bestLikeAlbum reduce(bestLikeAlbum b1, bestLikeAlbum b2) throws Exception {
                if(b1.getCount()>b2.getCount())
                    return b1;
                else
                    return b2;
            }
        });
        return outputStream;
    }

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
