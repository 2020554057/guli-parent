package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ClassName:GuliException
 * Package:IntelliJ IDEA
 * Description:
 *          这个是自定义异常处理类
 * @Author 吴苏杰
 * @Create 2023/10/25 10:38
 * @Version 1.0
 */
@Data
@AllArgsConstructor//有参构造
@NoArgsConstructor//无参构造
@ToString//从写toString方法的注解
public class GuliException extends RuntimeException{
    private Integer code;//状态码
    private String msg;//异常信息
}
