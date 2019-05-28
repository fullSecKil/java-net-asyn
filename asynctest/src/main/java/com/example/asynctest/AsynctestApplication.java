package com.example.asynctest;

import com.example.asynctest.config.TaskThreadPoolConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class AsynctestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsynctestApplication.class, args);
    }

}
