package edu.sicau.musicrestapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.sicau.musicrestapi.pojo.userEverySongsTimeAndTimes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface userTopTenSongsTimeAndTimesMapper {
    List<userEverySongsTimeAndTimes> selectUserEverySongsTimeAndTimes(@Param("userId") Integer userId);
}
