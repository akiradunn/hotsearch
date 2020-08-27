package com.akiradunn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.akiradunn.db.mapper")
public class HotSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotSearchApplication.class, args);
    }

}
