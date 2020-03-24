package com.akiradunn.hotsearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.akiradunn.hotsearch.dao")
public class HotsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotsearchApplication.class, args);
    }

}
