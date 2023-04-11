package edu.sicau.musicrestapi;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.sicau.musicrestapi.mapper")
public class MusicRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicRestApiApplication.class, args);
    }

}
