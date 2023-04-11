package edu.sicau.musicrestapi.mapper;

import edu.sicau.musicrestapi.pojo.songsHotRank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface artistHotRankMapper {
    public List<songsHotRank> getArtistHotRank(@Param("page") Integer page);
}
