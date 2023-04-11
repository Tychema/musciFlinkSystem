package edu.sicau.musicrestapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.sicau.musicrestapi.pojo.listenTimeCount;
import edu.sicau.musicrestapi.pojo.timeCount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface listenTimeCountMapper extends BaseMapper<listenTimeCount> {

}
