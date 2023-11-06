package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * ClassName:${NAME}
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/10/23 19:51
 * @Version 1.0
 */
@SpringBootApplication
//因为swaggerconfig使用了@Configuration，要使启动类扫描到，要加注解，不然只扫描当前包下的内容
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient//服务注册
@EnableFeignClients//使用OpenFeign服务调用的注解
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
