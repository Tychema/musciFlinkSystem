package edu.sicau.kafkaComsumer;

import edu.sicau.Sink.SinkToMysql;
import edu.sicau.beans.UserBehavior;
import edu.sicau.beans.songsHotRank;
import edu.sicau.beans.userEverySongsTimeAndTimes;
import edu.sicau.transform.bestLikeAlbumConsumer;
import edu.sicau.transform.firstListenComsumer;
import edu.sicau.transform.listenTimesConsumer;
import edu.sicau.transform.timeCountConsumer;
import edu.sicau.utils.stringToUserBehavior;
import edu.sicau.window.artistHotRankWindow;
import edu.sicau.window.songsHotRankWindow;
import edu.sicau.window.userSongsRankWindow;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.time.Duration;
import java.util.Properties;

public class kafkaDataConsumer {
    public static void main(String[] args) throws Exception {
        System.out.println("开始消费"+System.currentTimeMillis());
        //创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(5);

        //Kafka读入数据
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","hadoop101:9092");
        properties.setProperty("auto.offset.reset","latest");
        //FlinkKafkaConsumer(topic,反序列化工具,配置properties)
        DataStream<String> inputStream = env.addSource(new FlinkKafkaConsumer<String>("musicLog", new SimpleStringSchema(), properties));
        //本地读入数据
        //DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");
        //一行数据转为PoJo类
        stringToUserBehavior stringUtils = new stringToUserBehavior();
        DataStream<UserBehavior> dataStream=stringUtils.stringUtils(inputStream).assignTimestampsAndWatermarks(WatermarkStrategy.<UserBehavior>forBoundedOutOfOrderness(Duration.ofSeconds(15))
                .withTimestampAssigner(new SerializableTimestampAssigner<UserBehavior>() {
                    long nowTimesstamp=0;
                    @Override
                    public long extractTimestamp(UserBehavior u1, long l) {
                        if(u1!=null) {
                            //System.out.print(((long) u1.getPlayStartTime()) * 1000L + " ");
                            nowTimesstamp = ((long) u1.getPlayStartTime()) * 1000L;
                            return ((long) u1.getPlayStartTime()) * 1000L;
                        }
                        return nowTimesstamp;
                    }
                })
        );
        //写入
        SinkToMysql sinkToMysql = new SinkToMysql();
        //

        //用户窗口
        userSongsRankWindow usersongsrankwindow = new userSongsRankWindow();
        //最喜欢的专辑
        bestLikeAlbumConsumer bestLikeAlbum = new bestLikeAlbumConsumer();
        //第一次听
        firstListenComsumer firstListenComsumer = new firstListenComsumer();
        //次数统计
        listenTimesConsumer listenTimesConsumer = new listenTimesConsumer();
        //时间统计
        timeCountConsumer timeCountConsumer = new timeCountConsumer();
        //热度窗口
        songsHotRankWindow songsHotRank = new songsHotRankWindow();
        //歌手窗口
        artistHotRankWindow artistHotRankWindow = new artistHotRankWindow();
        //处理
        sinkToMysql.userEverySongsTimeAndTimesToMysql(userSongsRankWindow.userWindow(dataStream));
        sinkToMysql.sinkSongsHotRankToMysql(artistHotRankWindow.artistTimeWindow(dataStream),"artisthotrankwindow_day");
        sinkToMysql.sinkSongsHotRankToMysql(songsHotRank.songsTimeWindow(dataStream,24),"songshotrank_day");
        //sinkToMysql.sinkUserBehaviorToMysql(dataStream);
        //sinkToMysql.sinkbestLikeAlbumToMysql(bestLikeAlbum.bestLikeAlbumStreamOperator(dataStream));
        //sinkToMysql.sinkFirstListenToMysql(firstListenComsumer.firstListenCount(dataStream));
        //sinkToMysql.sinkListenTimeCountToMysql(listenTimesConsumer.listenTimeCount(dataStream));
        //sinkToMysql.sinkTimeCountToMysql(timeCountConsumer.timeCount(dataStream));
        //sinkToMysql.sinkTimeCountToMysql(timeCountConsumer.timeCount(dataStream));



        //执行
        env.execute();
    }
}
