package edu.sicau.Sink;

import edu.sicau.beans.*;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SinkToMysql {
    public SinkToMysql() {
    }


    public void sinkUserBehaviorToMysql(DataStream<UserBehavior> dataStream){
        dataStream.addSink(JdbcSink.sink("REPLACE INTO userbehavior VALUES (?,?,?,?,?,?,?,?,?,?)",
                ((preparedStatement, UserBehavior) -> {
                    preparedStatement.setInt(1,UserBehavior.getUserId());
                    preparedStatement.setInt(2,UserBehavior.getPlayStartTime());
                    preparedStatement.setInt(3,UserBehavior.getPlayEndTime());
                    preparedStatement.setInt(4,UserBehavior.getDt());
                    preparedStatement.setInt(5,UserBehavior.getSongId());
                    preparedStatement.setString(6,UserBehavior.getSongName());
                    preparedStatement.setInt(7,UserBehavior.getArtistId());
                    preparedStatement.setString(8,UserBehavior.getArtistName());
                    preparedStatement.setInt(9,UserBehavior.getAlbumId());
                    preparedStatement.setString(10,UserBehavior.getAlbumName());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicflinksystem_test?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
    }


    //总时间
    public void sinkTimeCountToMysql(DataStream<timeCount> dataStream){
        dataStream.addSink(JdbcSink.sink("REPLACE INTO TimeCount VALUES(?,?);",
                ((preparedStatement, timecount) -> {
                    preparedStatement.setInt(1,timecount.getUserId());
                    preparedStatement.setInt(2,timecount.getCount());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicflinksystem_test?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
    }


    //次数
    public void sinkListenTimeCountToMysql(DataStream<timeCount> dataStream){
        dataStream.addSink(JdbcSink.sink("REPLACE INTO listenTimeCount VALUES(?,?);",
                ((preparedStatement, timecount) -> {
                    preparedStatement.setInt(1,timecount.getUserId());
                    preparedStatement.setInt(2,timecount.getCount());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicflinksystem_test?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
    }


    //首次听歌
    public void sinkFirstListenToMysql(DataStream<UserBehavior> dataStream){
        dataStream.addSink(JdbcSink.sink("REPLACE INTO firstListen VALUES (?,?,?,?,?,?,?,?,?,?)",
                ((preparedStatement, UserBehavior) -> {
                    preparedStatement.setInt(1,UserBehavior.getUserId());
                    preparedStatement.setInt(2,UserBehavior.getPlayStartTime());
                    preparedStatement.setInt(3,UserBehavior.getPlayEndTime());
                    preparedStatement.setInt(4,UserBehavior.getDt());
                    preparedStatement.setInt(5,UserBehavior.getSongId());
                    preparedStatement.setString(6,UserBehavior.getSongName());
                    preparedStatement.setInt(7,UserBehavior.getArtistId());
                    preparedStatement.setString(8,UserBehavior.getArtistName());
                    preparedStatement.setInt(9,UserBehavior.getAlbumId());
                    preparedStatement.setString(10,UserBehavior.getAlbumName());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicflinksystem_test?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
    }


    //歌曲热度排行写入
    public void sinkSongsHotRankToMysql(DataStream<songsHotRank> dataStream,String tableName){
        dataStream.addSink(JdbcSink.sink("REPLACE INTO "+tableName+"(songId,songsCount,playStartTime,timeCount,songName,artistId,artistName) VALUES(?,?,?,?,?,?,?);",
                ((preparedStatement, e1) -> {
                    preparedStatement.setInt(1,e1.getSongId());
                    preparedStatement.setInt(2,e1.getSongsCount());
                    preparedStatement.setInt(3,e1.getPlayStartTime());
                    preparedStatement.setInt(4,e1.getSongsCount());
                    preparedStatement.setString(5,e1.getSongName());
                    preparedStatement.setInt(6,e1.getArtistId());
                    preparedStatement.setString(7,e1.getArtistName());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicflinksystem_test?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
    }
    //每个用户每一首歌听了几次听了多久
    public void userEverySongsTimeAndTimesToMysql(DataStream<userEverySongsTimeAndTimes> dataStream){
        dataStream.addSink(JdbcSink.sink("REPLACE INTO userEverySongsTimeAndTimes(userId,songId,songsCount,timeCount,songName,artistId,artistName,albumId,albumName) VALUES(?,?,?,?,?,?,?,?,?);",
                ((preparedStatement, e1) -> {
                    preparedStatement.setInt(1,e1.getUserId());
                    preparedStatement.setInt(2,e1.getSongId());
                    preparedStatement.setInt(3,e1.getSongsCount());
                    preparedStatement.setInt(4,e1.getTimeCount());
                    preparedStatement.setString(5,e1.getSongName());
                    preparedStatement.setInt(6,e1.getArtistId());
                    preparedStatement.setString(7,e1.getArtistName());
                    preparedStatement.setInt(8,e1.getAlbumId());
                    preparedStatement.setString(9,e1.getAlbumName());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicflinksystem_test?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
    }

    //最喜欢的专辑
    public void sinkbestLikeAlbumToMysql(DataStream<bestLikeAlbum> dataStream){
        dataStream.addSink(JdbcSink.sink("REPLACE INTO bestlikealbum(userid,albumid,albumname,count) VALUES(?,?,?,?);",
                ((preparedStatement, bestLikeAlbum) -> {
                    preparedStatement.setInt(1,bestLikeAlbum.getUserId());
                    preparedStatement.setInt(2,bestLikeAlbum.getAlbumId());
                    preparedStatement.setString(3,bestLikeAlbum.getAlbumName());
                    preparedStatement.setInt(4,bestLikeAlbum.getCount());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicflinksystem_test?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
    }





















    //Test
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> inputStream = env.readTextFile("D:\\1论文\\爬虫\\data\\mork\\morkLogs2\\test2.txt");
        DataStream<UserBehavior> dataStream=inputStream.map(new MapFunction<String, UserBehavior>() {
            @Override
            public UserBehavior map(String s) throws Exception {
                String[] s1 = s.split(" ");
                //return new bestLikeAlbum(Integer.valueOf(s1[0]),Integer.valueOf(s1[8]),s1[9],1);
                return new UserBehavior(Integer.valueOf(s1[0]),Integer.valueOf(s1[1]),Integer.valueOf(s1[2]),Integer.valueOf(s1[3]),Integer.valueOf(s1[4]),s1[5],Integer.valueOf(s1[6]),s1[7],Integer.valueOf(s1[8]),s1[9]);
            }
        });
        //sql模板,填充对应字段,JDBCConnectionOptions配置文件
        dataStream.addSink(JdbcSink.sink("INSERT INTO userbehavior VALUES (?,?,?,?,?,?,?,?,?,?)",
                ((preparedStatement, UserBehavior) -> {
                    preparedStatement.setInt(1,UserBehavior.getUserId());
                    preparedStatement.setInt(2,UserBehavior.getPlayStartTime());
                    preparedStatement.setInt(3,UserBehavior.getPlayEndTime());
                    preparedStatement.setInt(4,UserBehavior.getDt());
                    preparedStatement.setInt(5,UserBehavior.getSongId());
                    preparedStatement.setString(6,UserBehavior.getSongName());
                    preparedStatement.setInt(7,UserBehavior.getArtistId());
                    preparedStatement.setString(8,UserBehavior.getArtistName());
                    preparedStatement.setInt(9,UserBehavior.getAlbumId());
                    preparedStatement.setString(10,UserBehavior.getAlbumName());
                }),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder().withDriverName("com.mysql.jdbc.Driver").withUrl("jdbc:mysql://127.0.0.1:3306/musicFlinkSystem?characterEncoding=gbk&useSSL=false").withUsername("root").withPassword("1234").build()
        ));
        env.execute();
    }
}
