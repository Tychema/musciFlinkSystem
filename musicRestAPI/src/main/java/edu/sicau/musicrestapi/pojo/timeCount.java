package edu.sicau.musicrestapi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("timecount")
public class timeCount {
    private Integer userid;
    private Integer  count;

    public timeCount() {
    }

    public timeCount(Integer  userId, Integer  count) {
        this.userid = userId;
        this.count = count;
    }

    public Integer  getUserId() {
        return userid;
    }

    public void setUserId(Integer  userId) {
        this.userid = userId;
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
                "userId=" + userid +
                ", count=" + count +
                '}';
    }
}
