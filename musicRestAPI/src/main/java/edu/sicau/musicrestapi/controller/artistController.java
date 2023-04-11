package edu.sicau.musicrestapi.controller;

import edu.sicau.musicrestapi.mapper.artistHotRankMapper;
import edu.sicau.musicrestapi.pojo.songsHotRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class artistController {
    @Autowired
    private artistHotRankMapper artistHotRankMapper;
    @RequestMapping("/artistHotRank")
    public List<songsHotRank> getArtistHotRank(@RequestParam("page") String page){
        List<songsHotRank> artistHotRank = artistHotRankMapper.getArtistHotRank((Integer.valueOf(page)-1)*10);
        return artistHotRank;
    }
}
