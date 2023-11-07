package com.atguigu.msm.service;

import java.util.Map;

/**
 * ClassName:MsmService
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/7 15:56
 * @Version 1.0
 */
public interface MsmService {

    //发生阿里云短信验证码
    boolean send(Map<String, Object> param, String phone);
}
