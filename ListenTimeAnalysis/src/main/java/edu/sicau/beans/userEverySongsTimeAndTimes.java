package edu.sicau.beans;

//每个用户每一首歌听了几次听了多久
public class userEverySongsTimeAndTimes {
        private Integer id;
        private Integer userId;
        private Integer songId;
        //次数统计
        private Integer songsCount;
        private Integer playStartTime;
        //时间统计
        private Integer timeCount;

        private String songName;
        private Integer artistId;
        private String artistName;
        private Integer albumId;
        private String albumName;

        public Integer getTimeCount() {
            return timeCount;
        }

        public void setTimeCount(Integer timeCount) {
            this.timeCount = timeCount;
        }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public userEverySongsTimeAndTimes(Integer userId, Integer songId, Integer songsCount, Integer playStartTime, Integer timeCount, String songName, Integer artistId, String artistName, Integer albumId, String albumName) {
        this.userId = userId;
        this.songId = songId;
        this.songsCount = songsCount;
        this.playStartTime = playStartTime;
        this.timeCount = timeCount;
        this.songName = songName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "userEverySongsTimeAndTimes{" +
                "userId=" + userId +
                ", songId=" + songId +
                ", songsCount=" + songsCount +
                ", playStartTime=" + playStartTime +
                ", timeCount=" + timeCount +
                ", songName='" + songName + '\'' +
                ", artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                ", albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                '}';
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

    public userEverySongsTimeAndTimes() {
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

}
