package com.atguigu.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除数据库的自动配置
@ComponentScan(basePackages = {"com.atguigu"})//包扫描
@EnableDiscoveryClient//服务注册
public class VodApplicaton {
    public static void main(String[] args) {
        SpringApplication.run(VodApplicaton.class,args);
    }
}
