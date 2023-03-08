package edu.sicau.beans;

public class bestLikeAlbum {
    private Integer userId;
    private Integer albumId;
    private String albumName;
    private Integer count;

    public bestLikeAlbum() {
    }

    public bestLikeAlbum(Integer userId, Integer albumId, String albumName, Integer count) {
        this.userId = userId;
        this.albumId = albumId;
        this.albumName = albumName;
        this.count = count;
    }

    @Override
    public String toString() {
        return "bestLikeAlbum{" +
                "userId=" + userId +
                ", albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", count=" + count +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
