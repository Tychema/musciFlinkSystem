package edu.sicau.kafkaComsumer;

import edu.sicau.Sink.SinkToMysql;
import edu.sicau.beans.*;
import edu.sicau.transform.bestLikeAlbumConsumer;
import edu.sicau.transform.firstListenComsumer;
import edu.sicau.transform.listenTimesConsumer;
import edu.sicau.transform.timeCountConsumer;
import edu.sicau.window.artistHotRankWindow;
import edu.sicau.window.songsHotRankWindow;
import edu.sicau.window.userSongsRankWindow;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//本地路径数据测试
public class localDataConsumerTest {
    public static void main(String[] args) throws Exception {
        //创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
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
        songsHotRankWindow songsHotRankWindow = new songsHotRankWindow();
        //歌手窗口
        artistHotRankWindow artistHotRankWindow = new artistHotRankWindow();
        //处理
        //DataStream<userEverySongsTimeAndTimes> userEverySongsTimeAndTimesDataStream = usersongsrankwindow.userWindow(dataStream);
        //DataStream<bestLikeAlbum> albumStream=bestLikeAlbum.bestLikeAlbumStreamOperator(inputStream);
        //DataStream<UserBehavior> firstListenCountStream=firstListenComsumer.firstListenCount(inputStream);
        //DataStream<timeCount> listenTimeCountStream=listenTimesConsumer.listenTimeCount(inputStream);
        //DataStream<timeCount> timesCountStream=timeCountConsumer.timeCount(inputStream);
        //DataStream<songsHotRank> threesongsHotRankDataStream=songsHotRankWindow.songsTimeWindow(dataStream,3);
        //DataStream<songsHotRank> twelvesongsHotRankDataStream=songsHotRankWindow.songsTimeWindow(dataStream,12);
        //DataStream<songsHotRank> daysongsHotRankDataStream=songsHotRankWindow.songsTimeWindow(dataStream,24);
        //DataStream<songsHotRank> weeksongsHotRankDataStream=songsHotRankWindow.songsTimeWindow(dataStream,24*7);
        //DataStream<songsHotRank> monthsongsHotRankDataStream=songsHotRankWindow.songsTimeWindow(dataStream,24*30);
        //DataStream<songsHotRank> songsHotRankDataStream = artistHotRankWindow.artistTimeWindow(dataStream, 24);
        //写入
        SinkToMysql sinkToMysql = new SinkToMysql();
        //sinkToMysql.userEverySongsTimeAndTimesToMysql(usersongsrankwindow.userWindow(dataStream));
        //sinkToMysql.sinkbestLikeAlbumToMysql(bestLikeAlbum.bestLikeAlbumStreamOperator(dataStream));
        //sinkToMysql.sinkFirstListenToMysql(firstListenComsumer.firstListenCount(inputStream));
        //sinkToMysql.sinkListenTimeCountToMysql(listenTimesConsumer.listenTimeCount(inputStream));
        //sinkToMysql.sinkTimeCountToMysql(timeCountConsumer.timeCount(inputStream));
        //sinkToMysql.sinkSongsHotRankToMysql(songsHotRankWindow.songsTimeWindow(dataStream,3),"songshotrank_three");
        //sinkToMysql.sinkSongsHotRankToMysql(songsHotRankWindow.songsTimeWindow(dataStream,12),"songshotrank_twelve");
        //sinkToMysql.sinkSongsHotRankToMysql(songsHotRankWindow.songsTimeWindow(dataStream,24),"songshotrank_day");
        //sinkToMysql.sinkSongsHotRankToMysql(songsHotRankWindow.songsTimeWindow(dataStream,24*7),"songshotrank_week");
        //sinkToMysql.sinkSongsHotRankToMysql(songsHotRankWindow.songsTimeWindow(dataStream,24*30),"songshotrank_month");
        //sinkToMysql.sinkSongsHotRankToMysql(artistHotRankWindow.artistTimeWindow(dataStream, 24),"artisthotrankwindow_day");
        //sinkToMysql.sinkSongsHotRankToMysql(artistHotRankWindow.artistTimeWindow(dataStream, 24*7),"artisthotrankwindow_week");
        //sinkToMysql.sinkSongsHotRankToMysql(artistHotRankWindow.artistTimeWindow(dataStream, 24*30),"artisthotrankwindow_month");

        //执行
        env.execute();
    }
}
