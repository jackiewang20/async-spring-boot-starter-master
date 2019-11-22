package com.example.async.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author jackie wang
 * @Title: App
 * @ProjectName async-invoke-starter-master
 * @Description: TODO
 * @date 2019/11/21 11:28
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.example.async.web","com.example.async.starter"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
