package edu.sicau.musicrestapi;

import edu.sicau.musicrestapi.mapper.timeCountMapper;
import edu.sicau.musicrestapi.pojo.timeCount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPusTest {
    @Autowired
    private timeCountMapper timeCountMapper;

    @Test
    public void testSelectTimeCount(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId","300000005");
        List<timeCount> timeCounts = timeCountMapper.selectByMap(map);
        System.out.println(timeCounts.get(0));
    }

}
