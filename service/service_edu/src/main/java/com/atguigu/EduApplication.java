package com.atguigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@ComponentScan(basePackages = {"com.atguigu","com.atguigu.servicebase"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
