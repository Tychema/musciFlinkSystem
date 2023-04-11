package edu.sicau.musicrestapi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("firstlisten")
public class firstListen {
    private Integer userId;
    private Integer playStartTime;
    private Integer playEndTime;
    private Integer dt;
    private String songName;
    private Integer songId;
    private String artistName;
    private Integer artistId;
    private Integer albumId;
    private String albumName;

    public firstListen() {
    }

    public firstListen(Integer userId, Integer playStartTime, Integer playEndTime, Integer dt, String songName, Integer songId, String artistName, Integer artistId, Integer albumId, String albumName) {
        this.userId = userId;
        this.playStartTime = playStartTime;
        this.playEndTime = playEndTime;
        this.dt = dt;
        this.songName = songName;
        this.songId = songId;
        this.artistName = artistName;
        this.artistId = artistId;
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public Integer getuserId() {
        return userId;
    }

    public void setuserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlayStartTime() {
        return playStartTime;
    }

    public void setPlayStartTime(Integer playStartTime) {
        this.playStartTime = playStartTime;
    }

    public Integer getPlayEndTime() {
        return playEndTime;
    }

    public void setPlayEndTime(Integer playEndTime) {
        this.playEndTime = playEndTime;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "firstListen{" +
                "userId=" + userId +
                ", playStartTime=" + playStartTime +
                ", playEndTime=" + playEndTime +
                ", dt=" + dt +
                ", songName='" + songName + '\'' +
                ", songId=" + songId +
                ", artistName='" + artistName + '\'' +
                ", artistId=" + artistId +
                ", albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                '}';
    }
}
