package edu.sicau.beans;

public class timeCount {
    private Integer userId;
    private Integer  count;

    public timeCount() {
    }

    public timeCount(Integer  userId, Integer  count) {
        this.userId = userId;
        this.count = count;
    }

    public Integer  getUserId() {
        return userId;
    }

    public void setUserId(Integer  userId) {
        this.userId = userId;
    }

    public Integer  getCount() {
        return count;
    }

    public void setCount(Integer  count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "timeCount{" +
                "userId=" + userId +
                ", count=" + count +
                '}';
    }
}
