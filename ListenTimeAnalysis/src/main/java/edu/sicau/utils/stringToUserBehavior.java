package edu.sicau.utils;

import edu.sicau.beans.UserBehavior;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;

public class stringToUserBehavior {
    public  DataStream<UserBehavior> stringUtils(DataStream<String> dataStream){
        DataStream<UserBehavior> outputStream=dataStream.map(new MapFunction<String, UserBehavior>() {
            @Override
            public UserBehavior map(String s) throws Exception {
                String[] s1 = s.split(" ");
                Integer userId, playStartTime, playEndTime, dt, songId, artistId, albumId;
                String songName, artistName, albumName;
                try {
                    userId = Integer.valueOf(s1[0]);
                } catch (Exception e) {
                    userId = -1;
                }
                try {
                    playStartTime = Integer.valueOf(s1[1]);
                } catch (Exception e) {
                    playStartTime = 1640966400;
                }
                try {
                    playEndTime = Integer.valueOf(s1[2]);
                } catch (Exception e) {
                    playEndTime = -1;
                }
                try {
                    dt = Integer.valueOf(s1[3]);
                } catch (Exception e) {
                    dt = -1;
                }
                try {
                    songId = Integer.valueOf(s1[4]);
                } catch (Exception e) {
                    songId = -1;
                }
                try {
                    songName = s1[5];
                } catch (Exception e) {
                    songName = "None";
                }
                try {
                    artistId =Integer.valueOf(s1[6]);
                } catch (Exception e) {
                    artistId = -1;
                }
                try {
                    artistName = s1[7];
                } catch (Exception e) {
                    artistName = "None";
                }

                try {
                    albumId = Integer.valueOf(s1[8]);
                } catch (Exception e) {
                    albumId = -1;
                }
                try {
                    albumName = s1[9];
                } catch (Exception e) {
                    albumName = "None";
                }
                return new UserBehavior(userId,playStartTime,playEndTime,dt,songId,songName,artistId,artistName,albumId,albumName);
            }
        });
        return outputStream;
    }

}
