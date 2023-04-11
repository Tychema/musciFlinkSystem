package edu.sicau.musicrestapi.pojo;

import lombok.Data;

@Data
//每个用户每一首歌听了几次听了多久
public class songsHotRank {
    private Integer songId;
    //次数统计
    private Integer songsCount;
    private Integer playStartTime;
    //时间统计
    private Integer timeCount;

    private String songName;
    private Integer artistId;
    private String artistName;

    public Integer getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(Integer timeCount) {
        this.timeCount = timeCount;
    }

    public songsHotRank(Integer songId, Integer songsCount, Integer playStartTime, Integer timeCount, String songName, Integer artistId, String artistName) {
        this.songId = songId;
        this.songsCount = songsCount;
        this.playStartTime = playStartTime;
        this.timeCount = timeCount;
        this.songName = songName;
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public songsHotRank() {
    }

    public Integer getPlayStartTime() {
        return playStartTime;
    }

    public void setPlayStartTime(Integer playStartTime) {
        this.playStartTime = playStartTime;
    }


    public Integer getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(Integer songsCount) {
        this.songsCount = songsCount;
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

    @Override
    public String toString() {
        return "songsHotRank{" +
                "songId=" + songId +
                ", songsCount=" + songsCount +
                ", playStartTime=" + playStartTime +
                ", timeCount=" + timeCount +
                ", songName='" + songName + '\'' +
                ", artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
