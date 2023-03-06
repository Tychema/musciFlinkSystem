package edu.sicau.beans;

//每个用户每一首歌听了几次听了多久
public class everySongsTimeAndTimesCount {
    private Long userId;
    private Long songsCount;
    private String artistName;
    private Long artistId;
    private String songName;
    private Long songId;

    public everySongsTimeAndTimesCount() {
    }

    public everySongsTimeAndTimesCount(Long userId, Long songsCount, String artistName, Long artistId, String songName, Long songId) {
        this.userId = userId;
        this.songsCount = songsCount;
        this.artistName = artistName;
        this.artistId = artistId;
        this.songName = songName;
        this.songId = songId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(Long songsCount) {
        this.songsCount = songsCount;
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

    @Override
    public String toString() {
        return "everySongsTimeAndTimesCount{" +
                "userId=" + userId +
                ", songsCount=" + songsCount +
                ", artistName='" + artistName + '\'' +
                ", artistId=" + artistId +
                ", songName='" + songName + '\'' +
                ", songId=" + songId +
                '}';
    }
}
