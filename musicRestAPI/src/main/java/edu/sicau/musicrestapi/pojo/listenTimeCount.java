package edu.sicau.musicrestapi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("listentimecount")
public class listenTimeCount {
    private Integer userId;
    private Integer  count;

    public listenTimeCount() {
    }

    public listenTimeCount(Integer  userId, Integer  count) {
        this.userId = userId;
        this.count = count;
    }

    public Integer  getuserId() {
        return userId;
    }

    public void setuserId(Integer  userId) {
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
