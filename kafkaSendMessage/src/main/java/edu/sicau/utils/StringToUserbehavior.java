package edu.sicau.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StringToUserbehavior {
    private StreamExecutionEnvironment env;

    public StringToUserbehavior(StreamExecutionEnvironment env) {
        this.env = env;
    }

    public  DataStream<String> sendMessageUtils(String address) {
        DataStream<String> hdfsSource = env.readTextFile("hdfs://hadoop101:8020/MusicSystem/morkLog/" + address);
        //将hdfs文件路径打印输出
        DataStream<String> dataStream = hdfsSource
                .map(
                        line -> {
                            JSONObject obj;
                            try {
                                obj = (JSONObject) JSON.parse(line.replace("\\\"", "\"").replace("\'", "\"").replace("False", "\"Flase\"").replace("None", "\"None\""));
                            } catch (Exception e) {
                                return " ";
                            }
                            String userId, playStartTime, playEndTime, dt, songName, songId, artistName, artistId, albumId, albumName;
                            try {
                                userId = obj.getString("userId");
                            } catch (Exception e) {
                                userId = "None";
                            }
                            try {
                                playStartTime = obj.getString("playStartTime");
                            } catch (Exception e) {
                                playStartTime = "None";
                            }
                            try {
                                playEndTime = obj.getString("playEndTime");
                            } catch (Exception e) {
                                playEndTime = "None";
                                return null;
                            }
                            try {
                                dt = obj.getString("dt");
                            } catch (Exception e) {
                                dt = "None";
                            }
                            try {
                                songName = JSON.parseObject(obj.getString("data")).getString("name");
                            } catch (Exception e) {
                                songName = "None";
                            }
                            try {
                                songId = JSON.parseObject(obj.getString("data")).getString("id");
                            } catch (Exception e) {
                                songId = "None";
                            }
                            try {
                                artistName = JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("name");
                            } catch (Exception e) {
                                artistName = "None";
                            }
                            try {
                                artistId = JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("id");
                            } catch (Exception e) {
                                artistId = "None";
                            }
                            try {
                                albumId = JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("id");
                            } catch (Exception e) {
                                albumId = "None";
                            }
                            try {
                                albumName = JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("name");
                            } catch (Exception e) {
                                albumName = "None";
                            }
                            //按属性和分割赋值给对象
                            //return  new UserBehavior(userId,playStartTime,playEndTime,dt,songId,songName,artistId,artistName,albumId,artistName);
                            return new String(userId + " " + playStartTime + " " + playEndTime + " " + dt + " " + songId + " " + songName + " " + artistId + " " + artistName + " " + albumId + " " + albumName);
                        }
                );
        return dataStream;
    }
}
