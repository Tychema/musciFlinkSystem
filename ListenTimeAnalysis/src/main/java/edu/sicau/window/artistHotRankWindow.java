package edu.sicau.window;

import edu.sicau.Sink.SinkToMysql;
import edu.sicau.beans.UserBehavior;
import edu.sicau.beans.songsHotRank;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

//songsHotRank类与artistHotRank类结构操作一致
public class artistHotRankWindow {
    //每天热度统计
    public static DataStream<songsHotRank> artistTimeWindow(DataStream<UserBehavior> dataStream){
        DataStream<songsHotRank> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, songsHotRank>() {
                    @Override
                    public songsHotRank map(UserBehavior userBehavior) throws Exception {
                        return new songsHotRank(userBehavior.getSongId(), 1, userBehavior.getPlayStartTime(), userBehavior.getPlayEndTime() - userBehavior.getPlayStartTime(),  userBehavior.getSongName(), userBehavior.getArtistId(), userBehavior.getArtistName());
                    }
                });
                //水位线 乱序流
//                .assignTimestampsAndWatermarks(WatermarkStrategy.<songsHotRank>forBoundedOutOfOrderness(Duration.ofSeconds(1))
//                        .withTimestampAssigner(new SerializableTimestampAssigner<songsHotRank>() {
//                            @Override
//                            public long extractTimestamp(songsHotRank s1, long l) {
//                                return ((long)s1.getPlayStartTime())*1000;
//                            }
//                        }));
        //滚动时间窗口

        DataStream<songsHotRank> artistHourTimewindow = dataStreamWithWaterMark.keyBy(artistHotRank -> artistHotRank.getArtistId())
//                .window(TumblingEventTimeWindows.of(Time.hours(time)))
                .reduce(new ReduceFunction<songsHotRank>() {
                    @Override
                    public songsHotRank reduce(songsHotRank e1, songsHotRank e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount()+1);
                        e1.setTimeCount(e1.getTimeCount()+e2.getTimeCount());
                        return e1;                    }
                });
        return artistHourTimewindow;
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //env.getConfig().setAutoWatermarkInterval(100);
        //读入数据
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\2022-01-01.txt");
        //一行数据转为PoJo类
        DataStream<UserBehavior> dataStream=inputStream.map(new MapFunction<String, UserBehavior>() {
            @Override
            public UserBehavior map(String s) throws Exception {
                String[] s1 = s.split(" ");
                //return new bestLikeAlbum(Integer.valueOf(s1[0]),Integer.valueOf(s1[8]),s1[9],1);
                return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });

        DataStream<songsHotRank> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, songsHotRank>() {
            @Override
            public songsHotRank map(UserBehavior userBehavior) throws Exception {
                return new songsHotRank(userBehavior.getSongId(), 1, userBehavior.getPlayStartTime(), userBehavior.getPlayEndTime() - userBehavior.getPlayStartTime(),  userBehavior.getSongName(), userBehavior.getArtistId(), userBehavior.getArtistName());
            }
        });

        DataStream<songsHotRank> artistHourTimewindow = dataStreamWithWaterMark.keyBy(artistHotRank -> artistHotRank.getArtistId())
//                .window(TumblingEventTimeWindows.of(Time.hours(time)))
                .reduce(new ReduceFunction<songsHotRank>() {
                    @Override
                    public songsHotRank reduce(songsHotRank e1, songsHotRank e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount()+1);
                        e1.setTimeCount(e1.getTimeCount()+e2.getTimeCount());
                        return e1;                    }
                });
        SinkToMysql sinkToMysql = new SinkToMysql();
        sinkToMysql.sinkSongsHotRankToMysql(artistHourTimewindow,"artisthotrankwindow_day");
        env.execute();
    }
}
