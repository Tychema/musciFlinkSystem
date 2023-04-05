package edu.sicau.window;

import edu.sicau.beans.UserBehavior;
import edu.sicau.beans.songsHotRank;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

public class songsHotRankWindow {

    //十二个小时热度统计
    public static DataStream<songsHotRank> twelveHourTimeWindow(DataStream<UserBehavior> dataStream){
        DataStream<songsHotRank> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, songsHotRank>() {
                    @Override
                    public songsHotRank map(UserBehavior userBehavior) throws Exception {
                        return new songsHotRank(userBehavior.getSongId(), 1, userBehavior.getPlayStartTime(), userBehavior.getPlayEndTime() - userBehavior.getPlayStartTime(),  userBehavior.getSongName(), userBehavior.getArtistId(), userBehavior.getArtistName());
                    }
                })
                //水位线 乱序流
                .assignTimestampsAndWatermarks(WatermarkStrategy.<songsHotRank>forBoundedOutOfOrderness(Duration.ofHours(1))
                        .withTimestampAssigner(new SerializableTimestampAssigner<songsHotRank>() {
                            @Override
                            public long extractTimestamp(songsHotRank everySongsTimeAndTimesCount, long l) {
                                return everySongsTimeAndTimesCount.getPlayStartTime()*1000;
                            }
                        }));
        //滚动时间窗口
        DataStream<songsHotRank> twelveHourTimewindow = dataStreamWithWaterMark.keyBy(songsHotRank -> songsHotRank.getSongId())
                .window(TumblingEventTimeWindows.of(Time.hours(12)))
                .reduce(new ReduceFunction<songsHotRank>() {
                    @Override
                    public songsHotRank reduce(songsHotRank e1, songsHotRank e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount()+1);
                        e1.setTimeCount(e1.getTimeCount()+e2.getTimeCount());
                        return e1;                    }
                });
        return twelveHourTimewindow;
    }

    //三个小时热度统计
    public static DataStream<songsHotRank> threeHourTimeWindow(DataStream<UserBehavior> dataStream){
        DataStream<songsHotRank> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, songsHotRank>() {
                    @Override
                    public songsHotRank map(UserBehavior userBehavior) throws Exception {
                        return new songsHotRank(userBehavior.getSongId(), 1, userBehavior.getPlayStartTime(), userBehavior.getPlayEndTime() - userBehavior.getPlayStartTime(),  userBehavior.getSongName(), userBehavior.getArtistId(), userBehavior.getArtistName());
                    }
                })
                //水位线 乱序流
                .assignTimestampsAndWatermarks(WatermarkStrategy.<songsHotRank>forBoundedOutOfOrderness(Duration.ofHours(1))
                        .withTimestampAssigner(new SerializableTimestampAssigner<songsHotRank>() {
                            @Override
                            public long extractTimestamp(songsHotRank everySongsTimeAndTimesCount, long l) {
                                return everySongsTimeAndTimesCount.getPlayStartTime()*1000;
                            }
                        }));
        //滚动时间窗口
        DataStream<songsHotRank> threeHourTimewindow = dataStreamWithWaterMark.keyBy(songsHotRank -> songsHotRank.getSongId())
                .window(TumblingEventTimeWindows.of(Time.hours(3)))
                .reduce(new ReduceFunction<songsHotRank>() {
                    @Override
                    public songsHotRank reduce(songsHotRank e1, songsHotRank e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount()+1);
                        e1.setTimeCount(e1.getTimeCount()+e2.getTimeCount());
                        return e1;
                    }
                });
        return threeHourTimewindow;
    }

    //六小时热度统计
    public static DataStream<songsHotRank> sixHourTimeWindow(DataStream<UserBehavior> dataStream){
        DataStream<songsHotRank> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, songsHotRank>() {
                    @Override
                    public songsHotRank map(UserBehavior userBehavior) throws Exception {
                        return new songsHotRank(userBehavior.getSongId(), 1, userBehavior.getPlayStartTime(), userBehavior.getPlayEndTime() - userBehavior.getPlayStartTime(),  userBehavior.getSongName(), userBehavior.getArtistId(), userBehavior.getArtistName());
                    }
                })
                //水位线 乱序流
                .assignTimestampsAndWatermarks(WatermarkStrategy.<songsHotRank>forBoundedOutOfOrderness(Duration.ofHours(1))
                        .withTimestampAssigner(new SerializableTimestampAssigner<songsHotRank>() {
                            @Override
                            public long extractTimestamp(songsHotRank everySongsTimeAndTimesCount, long l) {
                                return everySongsTimeAndTimesCount.getPlayStartTime()*1000;
                            }
                        }));
        //滚动时间窗口
        DataStream<songsHotRank> sixHourTimewindow = dataStreamWithWaterMark.keyBy(songsHotRank -> songsHotRank.getSongId())
                .window(TumblingEventTimeWindows.of(Time.hours(6)))
                .reduce(new ReduceFunction<songsHotRank>() {
                    @Override
                    public songsHotRank reduce(songsHotRank e1, songsHotRank e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount()+1);
                        e1.setTimeCount(e1.getTimeCount()+e2.getTimeCount());
                        return e1;
                    }
                });
        return sixHourTimewindow;
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //env.getConfig().setAutoWatermarkInterval(100);
        //读入数据
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");
        //一行数据转为PoJo类

        DataStream<UserBehavior> dataStream=inputStream.map(new MapFunction<String, UserBehavior>() {
            @Override
            public UserBehavior map(String s) throws Exception {
                String[] s1 = s.split(" ");
                //return new bestLikeAlbum(Integer.valueOf(s1[0]),Integer.valueOf(s1[8]),s1[9],1);
                return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
        //转换为新类
        DataStream<songsHotRank> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, songsHotRank>() {
                    @Override
                    public songsHotRank map(UserBehavior userBehavior) throws Exception {
                        return new songsHotRank(userBehavior.getSongId(), 1, userBehavior.getPlayStartTime(), userBehavior.getPlayEndTime() - userBehavior.getPlayStartTime(),  userBehavior.getSongName(), userBehavior.getArtistId(), userBehavior.getArtistName());
                    }
                })
                //水位线 乱序流
                .assignTimestampsAndWatermarks(WatermarkStrategy.<songsHotRank>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                        .withTimestampAssigner(new SerializableTimestampAssigner<songsHotRank>() {
                            @Override
                            public long extractTimestamp(songsHotRank everySongsTimeAndTimesCount, long l) {
                                return everySongsTimeAndTimesCount.getPlayStartTime()*1000;
                        }
                }));
        //滚动时间窗口
        dataStreamWithWaterMark.keyBy(songsHotRank -> songsHotRank.getSongId())
                .window(TumblingEventTimeWindows.of(Time.hours(1)))
                .reduce(new ReduceFunction<songsHotRank>() {
                    @Override
                    public songsHotRank reduce(songsHotRank e1, songsHotRank e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount()+1);
                        e1.setTimeCount(e1.getTimeCount()+e2.getTimeCount());
                        return e1;
                    }
                }).print();
        //滑动时间窗口
//        dataStreamWithWaterMark.keyBy(everySongsTimeAndTimesCount->everySongsTimeAndTimesCount.getSongId()).window(TumblingEventTimeWindows.of(Time.hours(3),Time.minutes(30))).reduce(new ReduceFunction<everySongsTimeAndTimesCount>() {
//            @Override
//            public everySongsTimeAndTimesCount reduce(everySongsTimeAndTimesCount e1, everySongsTimeAndTimesCount e2) throws Exception {
//                return new everySongsTimeAndTimesCount(e1.getSongId(), e1.getSongsCount() + 1, e1.getPlayStartTime(), e1.getTimeCount() + e2.getTimeCount(),  e1.getSongName(), e1.getArtistId(), e1.getArtistName());
//            }
//        }).print();
        /**x
         * 开窗
         * 无KeyBy情况（通常需要KeyBy后再开窗）
         * 传一个windowAssiginer窗口分配器
         */
        //一个小时为一个窗口
        //WindowedStream<everySongsTimeAndTimesCount, Tuple, TimeWindow> window = dataStream1.keyBy("songId").window(TumblingProcessingTimeWindows.of(Time.hours(1))); //滚动时间窗口
        //一小时为一个窗口，间隔5分钟
        //AllWindowedStream<UserBehavior, TimeWindow> window = dataStream.windowAll(SlidingEventTimeWindows.of(Time.hours(1),Time.minutes(5))); //滑动时间窗口
        //两秒钟为一个会话
        //AllWindowedStream<UserBehavior, TimeWindow> window = dataStream.windowAll(EventTimeSessionWindows.withGap(Time.seconds(2))); //事件时间会话窗口
        //滑动计数、十个数为一个窗口、每次滑动2个数。滚动只留第一个参数
        //AllWindowedStream<UserBehavior, TimeWindow> window = dataStream.countWindowAll(10,2);
        /**
         * 窗口函数（窗口算子）
         */
        env.execute();
    }
}
