package edu.sicau.musicrestapi.controller;

import edu.sicau.musicrestapi.mapper.songsHotRankMapper;
import edu.sicau.musicrestapi.pojo.songsHotRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class songController {
    @Autowired
    private songsHotRankMapper songsHotRankMapper;
    @RequestMapping("/songsHotRank")
    public List<songsHotRank> getSongsHotRank(@RequestParam("page") String page){
        List<songsHotRank> songsHotRank = songsHotRankMapper.getSongsHotRank((Integer.valueOf(page)-1)*10);
        return songsHotRank;
    }
}
