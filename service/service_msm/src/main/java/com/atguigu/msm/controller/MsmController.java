package com.atguigu.msm.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.EmailUtil;
import com.atguigu.msm.utils.RandomUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:MsmController
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/7 15:55
 * @Version 1.0
 */
@Api(description = "阿里云短信验证服务")
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    //旧版本的使用方式
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    //发送短信的方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {

        // 从redis获取验证码，如果能获取，直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        // 获取不到就阿里云发送
        // 生成随机数，传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        // 调用service发送短信的方法
        //调用阿里云提供的短信服务API完成短信
        //boolean isSend = SMSUtils.sendMessage("阿里云短信测试", "SMS_284910636", phone, code);
        //调用邮箱验证
        boolean isSend = EmailUtil.sendAuthCodeEmail(phone, code);

        //boolean isSend = msmService.send(param, phone);
        if (isSend) {
            // 如果发送成功，把发送成功的code验证码保存到redis中，并设置有效时间，设置5分钟内有效
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }

    }

}
