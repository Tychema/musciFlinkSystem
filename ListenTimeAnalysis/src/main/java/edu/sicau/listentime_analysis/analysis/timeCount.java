package edu.sicau.listentime_analysis.analysis;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
import edu.sicau.listentime_analysis.beans.UserBehavior;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class timeCount {
    public static void main(String[] args) throws Exception{
        //1、创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        //2、从TXT读取数据，创建DataStream
        DataStream<String> inputStream = env.readTextFile("");

        //3、转换为POJO，分配时间戳和watermark
        //读取一行，分成String[]数组，转换成POJO一个类
        /**
         *    private Long userId;
         *    private Long playStartTime;
         *    private Long playEndTime;
         *    private Long dt;
         *    private String songName;
         *    private Long songId;
         *    private String artistName;
         *    private Long artistId;
         *    private Long albumId;
         *    private String albumName;
         */
//        DataStream<UserBehavior> dataStream=inputStream
//                .map(
//                        line ->{
//                            JSONObject obj= JSON.parseObject(line);
//                            Long userId=Long.parseLong(obj.getString("userId"));
//                            Long playStartTime=Long.parseLong(obj.getString("playStartTime"));
//                            Long playEndTime=Long.parseLong(obj.getString("playEndTime"));;
//                            Long dt=Long.parseLong(obj.getString("dt"));;
//                            String songName=JSON.parseObject(obj.getString("data")).getString("name");
//                            Long songId=Long.parseLong(JSON.parseObject(obj.getString("data")).getString("id"));
//                            String artistName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("name");
//                            Long artistId=Long.parseLong(JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("id"));
//                            Long albumId=Long.parseLong(JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("id"));
//                            String albumName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("name");
//                            //按属性和分割赋值给对象
//                            return new UserBehavior(userId,playStartTime,playEndTime,dt,songName,songId,artistName,artistId,albumId,albumName);
//                        }
//                ).assignTimestampsAndWatermarks(new AscendingTimestampExtractor<UserBehavior>() {
//                    @Override
//                    public long extractAscendingTimestamp(UserBehavior element) {
//                        return element.getPlayStartTime()*1000L;
//                    }
//                });
        //4、分组开窗聚合，得到每个窗口的count值
      //  DataStream<timeCount> windowAggStream=dataStream
      //          .keyBy("userId")
      //          .timeWindow(Time.hours(1),Time.minutes(5))
      //          .aggregate();
        env.execute("All Time Count");
    }
}
