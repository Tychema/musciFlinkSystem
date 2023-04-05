package edu.sicau.kafkaComsumer;

import edu.sicau.Sink.SinkToMysql;
import edu.sicau.beans.*;
import edu.sicau.transform.bestLikeAlbumConsumer;
import edu.sicau.transform.firstListenComsumer;
import edu.sicau.transform.listenTimesConsumer;
import edu.sicau.transform.timeCountConsumer;
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
        //处理
        //DataStream<userEverySongsTimeAndTimes> userEverySongsTimeAndTimesDataStream = usersongsrankwindow.userWindow(dataStream);
        //DataStream<bestLikeAlbum> albumStream=bestLikeAlbum.bestLikeAlbumStreamOperator(inputStream);
        //DataStream<UserBehavior> firstListenCountStream=firstListenComsumer.firstListenCount(inputStream);
        //DataStream<timeCount> listenTimeCountStream=listenTimesConsumer.listenTimeCount(inputStream);
        //DataStream<timeCount> timesCountStream=timeCountConsumer.timeCount(inputStream);
        DataStream<songsHotRank> threesongsHotRankDataStream=songsHotRankWindow.threeHourTimeWindow(dataStream);
        DataStream<songsHotRank> sixsongsHotRankDataStream=songsHotRankWindow.sixHourTimeWindow(dataStream);
        DataStream<songsHotRank> twelvesongsHotRankDataStream=songsHotRankWindow.twelveHourTimeWindow(dataStream);
        //写入
        SinkToMysql sinkToMysql = new SinkToMysql();
        //sinkToMysql.userEverySongsTimeAndTimesToMysql(userEverySongsTimeAndTimesDataStream);
        //sinkToMysql.sinkbestLikeAlbumToMysql(albumStream);
        //sinkToMysql.sinkFirstListenToMysql(firstListenCountStream);
        //sinkToMysql.sinkListenTimeCountToMysql(listenTimeCountStream);
        //sinkToMysql.sinkTimeCountToMysql(timesCountStream);
        sinkToMysql.threesinkSongsHotRankToMysql(threesongsHotRankDataStream);
        sinkToMysql.sixsinkSongsHotRankToMysql(sixsongsHotRankDataStream);
        sinkToMysql.twelvesinkSongsHotRankToMysql(twelvesongsHotRankDataStream);
        //执行
        env.execute();
    }
}
