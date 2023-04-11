package edu.sicau.musicrestapi.mapper;

import edu.sicau.musicrestapi.pojo.songsHotRank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface songsHotRankMapper {
    public List<songsHotRank> getSongsHotRank(@Param("page") Integer page);
}
