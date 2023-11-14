package com.atguigu.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ClassName:${NAME}
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/14 12:25
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.atguigu.staservice.mapper")
@ComponentScan("com.atguigu")
@EnableDiscoveryClient//开启服务发现
@EnableFeignClients//开启Feign远程调用
@EnableScheduling//开启定时任务
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}