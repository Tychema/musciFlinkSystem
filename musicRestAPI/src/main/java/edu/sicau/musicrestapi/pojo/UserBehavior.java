package edu.sicau.musicrestapi.pojo;


import lombok.Data;

//userId 用户ID
//playStartTime 播放开始时间
//playEndTime 播放结束时间
//dt 歌曲长度
//data.name 歌曲名
//data.id 歌曲ID
//data.ar.id 歌手ID
//data.ar.name 歌手名字
//data.al.id 专辑ID
//data.al.name 专辑名字
@Data
public class UserBehavior {
    //定义私有属性
    private Integer userId;
    private Integer playStartTime;
    private Integer playEndTime;
    private Integer dt;
    private Integer songId;
    private String songName;
    private Integer artistId;
    private String artistName;
    private Integer albumId;
    private String albumName;

    public UserBehavior() {
    }

    public UserBehavior(Integer userId, Integer playStartTime, Integer playEndTime, Integer dt, Integer songId, String songName, Integer artistId, String artistName, Integer albumId, String albumName) {
        this.userId = userId;
        this.playStartTime = playStartTime;
        this.playEndTime = playEndTime;
        this.dt = dt;
        this.songId = songId;
        this.songName = songName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "UserBehavior{" +
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
}
