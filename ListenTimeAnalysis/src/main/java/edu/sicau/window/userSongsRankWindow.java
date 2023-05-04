package edu.sicau.window;

import edu.sicau.beans.UserBehavior;
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
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.Random;

public class userSongsRankWindow {
    public static DataStream<userEverySongsTimeAndTimes> userWindow(DataStream<UserBehavior> dataStream){
        //转换为新类
        DataStream<userEverySongsTimeAndTimes> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, userEverySongsTimeAndTimes>() {
                    @Override
                    public userEverySongsTimeAndTimes map(UserBehavior u1) throws Exception {
                        return new userEverySongsTimeAndTimes(u1.getUserId(),u1.getSongId(), 1, u1.getPlayStartTime(), u1.getPlayEndTime() - u1.getPlayStartTime(),  u1.getSongName(), u1.getArtistId(), u1.getArtistName(),u1.getAlbumId(),u1.getAlbumName());
                    }
                });
                //水位线 乱序流
//                .assignTimestampsAndWatermarks(WatermarkStrategy.<userEverySongsTimeAndTimes>forBoundedOutOfOrderness(Duration.ofSeconds(1))
//                        .withTimestampAssigner(new SerializableTimestampAssigner<userEverySongsTimeAndTimes>() {
//                            @Override
//                            public long extractTimestamp(userEverySongsTimeAndTimes s1, long l) {
//                                return ((long)s1.getPlayStartTime())*1000;
//                            }
//                        }));
        //dataStreamWithWaterMark.print();
        //滚动时间窗口
        DataStream<userEverySongsTimeAndTimes> userWindow = dataStreamWithWaterMark
                //shuffle
                .map(new MapFunction<userEverySongsTimeAndTimes, userEverySongsTimeAndTimes>() {
                    Random random=new Random();
                    @Override
                    public userEverySongsTimeAndTimes map(userEverySongsTimeAndTimes u1) throws Exception {
                        u1.setUserId(u1.getUserId()*100+random.nextInt(99));
                        return u1;
                    }
                })
                .keyBy(new KeySelector<userEverySongsTimeAndTimes, Tuple2<Integer,Integer>>() {
                    @Override
                    public Tuple2<Integer,Integer> getKey(userEverySongsTimeAndTimes u1) throws Exception {
                        return new Tuple2<Integer,Integer>(u1.getUserId(),u1.getSongId());
                    }
                })
                //dataStreamWithWaterMark.keyBy("userId","songId")
                //一年的数据
                .window(TumblingEventTimeWindows.of(Time.days(1)))
                .reduce(new ReduceFunction<userEverySongsTimeAndTimes>() {
                    @Override
                    public userEverySongsTimeAndTimes reduce(userEverySongsTimeAndTimes e1, userEverySongsTimeAndTimes e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount() + 1);
                        e1.setTimeCount(e1.getTimeCount() + e2.getTimeCount());
                        return e1;
                    }
                }, new ProcessWindowFunction<userEverySongsTimeAndTimes, userEverySongsTimeAndTimes, Tuple2<Integer, Integer>, TimeWindow>() {
                    @Override
                    public void process(Tuple2<Integer, Integer> integerIntegerTuple2, ProcessWindowFunction<userEverySongsTimeAndTimes, userEverySongsTimeAndTimes, Tuple2<Integer, Integer>, TimeWindow>.Context context, Iterable<userEverySongsTimeAndTimes> elements, Collector<userEverySongsTimeAndTimes> out) throws Exception {
                        userEverySongsTimeAndTimes next = elements.iterator().next();
                        next.setUserId(next.getUserId()/10);
                        out.collect(next);
                    }
                })
                .keyBy(new KeySelector<userEverySongsTimeAndTimes, Tuple2<Integer,Integer>>() {
                    @Override
                        public Tuple2<Integer,Integer> getKey(userEverySongsTimeAndTimes u1) throws Exception {
                            return new Tuple2<Integer,Integer>(u1.getUserId(),u1.getSongId());
                        }
                    }
                ).reduce(new ReduceFunction<userEverySongsTimeAndTimes>() {
                    @Override
                    public userEverySongsTimeAndTimes reduce(userEverySongsTimeAndTimes e1, userEverySongsTimeAndTimes e2) throws Exception {
                        e1.setSongsCount(e1.getSongsCount() + 1);
                        e1.setTimeCount(e1.getTimeCount() + e2.getTimeCount());
                        return e1;
                        }
                    }
                );
        //userWindow.print();
        return userWindow;
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
        DataStream<userEverySongsTimeAndTimes> dataStreamWithWaterMark = dataStream.map(new MapFunction<UserBehavior, userEverySongsTimeAndTimes>() {
                    @Override
                    public userEverySongsTimeAndTimes map(UserBehavior u1) throws Exception {
                        return new userEverySongsTimeAndTimes(u1.getUserId(),u1.getSongId(), 1, u1.getPlayStartTime(), u1.getPlayEndTime() - u1.getPlayStartTime(),  u1.getSongName(), u1.getArtistId(), u1.getArtistName(),u1.getAlbumId(),u1.getAlbumName());
                    }
                })
                //水位线 乱序流
                .assignTimestampsAndWatermarks(WatermarkStrategy.<userEverySongsTimeAndTimes>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                        .withTimestampAssigner(new SerializableTimestampAssigner<userEverySongsTimeAndTimes>() {
                            @Override
                            public long extractTimestamp(userEverySongsTimeAndTimes s1, long l) {
                                return s1.getPlayStartTime();
                            }
                        }));
        //滚动时间窗口
        dataStreamWithWaterMark.keyBy(new KeySelector<userEverySongsTimeAndTimes, Tuple2<Integer,Integer>>() {
                    @Override
                    public Tuple2<Integer,Integer> getKey(userEverySongsTimeAndTimes u1) throws Exception {
                        return new Tuple2<Integer,Integer>(u1.getUserId(),u1.getSongId());
                    }
                })
        //dataStreamWithWaterMark.keyBy("userId","songId")
                //一年的数据
                .countWindow(1000)
                //.window(TumblingEventTimeWindows.of(Time.days(365)))
                .reduce(new ReduceFunction<userEverySongsTimeAndTimes>() {
                    @Override
                    public userEverySongsTimeAndTimes reduce(userEverySongsTimeAndTimes e1, userEverySongsTimeAndTimes e2) throws Exception {
                        e2.setSongsCount(e1.getSongsCount()+1);
                        e2.setTimeCount(e1.getTimeCount() + e2.getTimeCount());
                        return e2;                    }
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
