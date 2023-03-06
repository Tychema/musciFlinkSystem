package edu.sicau.beans;

public class timeCount {
    private Long userId;
    private Long count;

    public timeCount() {
    }

    public timeCount(Long userId, Long count) {
        this.userId = userId;
        this.count = count;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
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
