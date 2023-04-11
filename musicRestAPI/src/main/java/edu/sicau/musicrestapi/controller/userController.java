package edu.sicau.musicrestapi.controller;

import edu.sicau.musicrestapi.mapper.*;
import edu.sicau.musicrestapi.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private timeCountMapper timeCountMapper;

    @Autowired
    private listenTimeCountMapper listenTimeCountMapper;

    @Autowired
    private firstListenMapper firstListenMapper;

    @Autowired
    private bestLikeAlbumMapper bestLikeAlbumMapper;

    @Autowired
    private userTopTenSongsTimeAndTimesMapper userTopTenSongsTimeAndTimesMapper;

    @RequestMapping("/bestLikeAlbum")
    public bestLikeAlbum getBestLikeAlbum(@RequestParam("userId") String userId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        List<bestLikeAlbum> bestLikeAlbums = bestLikeAlbumMapper.selectByMap(map);
        return bestLikeAlbums.get(0);
    }
    @RequestMapping("/firstListen")
    public firstListen getFirstListen(@RequestParam("userId") String userId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        List<firstListen> firstListens = firstListenMapper.selectByMap(map);
        return firstListens.get(0);
    }
    @RequestMapping("/timeCount")
    public timeCount getTimeCount(@RequestParam("userId") String userId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        List<timeCount> timeCounts = timeCountMapper.selectByMap(map);
        return timeCounts.get(0);
    }
    @RequestMapping("/listenTimeCount")
    public listenTimeCount getListenTimeCount(@RequestParam("userId") String userId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        List<listenTimeCount> listenTimeCounts = listenTimeCountMapper.selectByMap(map);
        return listenTimeCounts.get(0);
    }
    @RequestMapping("/userEverySongsTimeAndTimes")
    public List<userEverySongsTimeAndTimes> getUserTopTenSongsTimeAndTimes(@RequestParam("userId") String userId){
        List<userEverySongsTimeAndTimes> userEverySongsTimeAndTimes = userTopTenSongsTimeAndTimesMapper.selectUserEverySongsTimeAndTimes(Integer.valueOf(userId));
        return userEverySongsTimeAndTimes;
    }
}
