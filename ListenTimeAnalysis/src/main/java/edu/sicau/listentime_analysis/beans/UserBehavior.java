package edu.sicau.listentime_analysis.beans;


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
public class UserBehavior {
    //定义私有属性
    private Long userId;
    private Long playStartTime;
    private Long playEndTime;
    private Long dt;
    private String songName;
    private Long songId;
    private String artistName;
    private Long artistId;
    private Long albumId;
    private String albumName;

    public UserBehavior() {
    }

    public UserBehavior(Long userId, Long playStartTime, Long playEndTime, Long dt, String songName, Long songId, String artistName, Long artistId, Long albumId, String albumName) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlayStartTime() {
        return playStartTime;
    }

    public void setPlayStartTime(Long playStartTime) {
        this.playStartTime = playStartTime;
    }

    public Long getPlayEndTime() {
        return playEndTime;
    }

    public void setPlayEndTime(Long playEndTime) {
        this.playEndTime = playEndTime;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
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
        return "beans{" +
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
