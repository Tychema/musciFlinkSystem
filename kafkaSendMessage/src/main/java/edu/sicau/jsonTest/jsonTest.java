package edu.sicau.jsonTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class jsonTest {
    public static void main(String[] args) {
        String result ="{'userId': '300017958', 'playStartTime': '1641427728', 'playEndTime': '1641427983', 'resourceType': 'SONG', 'data': {'name': \"What's\", 'id': '1824933354', 'pst': 0, 't': 0, 'ar': {'id': '53283', 'name': 'Drake', 'tns': [], 'alias': []}, 'alia': [], 'pop': 95, 'st': 0, 'rt': '', 'fee': 8, 'v': 29, 'crbt': None, 'cf': '', 'al': {'id': '1824933354', 'name': '123900536', 'tns': []}, 'dt': 343714, 'a': None, 'cd': '1', 'no': 2, 'rtUrl': None, 'ftype': 0, 'rtUrls': [], 'djId': 0, 'copyright': 2, 's_id': 0, 'originCoverType': 0, 'originSongSimpleData': None, 'single': 0, 'noCopyrightRcmd': None, 'mst': 9, 'cp': 743010, 'mv': 0, 'rtype': 0, 'rurl': None, 'publishTime': 1337702400007}, 'banned': False, 'dt': '255'}";
        JSONObject obj = (JSONObject) JSON.parse(result.replace("\'","\"").replace("False","\"Flase\"").replace("None","\"None\""));
        Long userId=Long.parseLong(obj.getString("userId"));
        Long playStartTime=Long.parseLong(obj.getString("playStartTime"));
        Long playEndTime=Long.parseLong(obj.getString("playEndTime"));;
        Long dt=Long.parseLong(obj.getString("dt"));;
        String songName=JSON.parseObject(obj.getString("data")).getString("name");
        Long songId=Long.parseLong(JSON.parseObject(obj.getString("data")).getString("id"));
        String artistName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("name");
        Long artistId=Long.parseLong(JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("ar")).getString("id"));
        String albumId=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("id");
        String albumName=JSON.parseObject(JSON.parseObject(obj.getString("data")).getString("al")).getString("name");}
}
