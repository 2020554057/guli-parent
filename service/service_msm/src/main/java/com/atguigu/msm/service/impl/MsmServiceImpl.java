package com.atguigu.msm.service.impl;

//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.CommonRequest;
//import com.aliyuncs.CommonResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;
//import com.atguigu.msm.service.MsmService;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;

import com.atguigu.msm.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ClassName:MsmServiceImpl
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/7 15:56
 * @Version 1.0
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送验证码
     *
     * @param param 验证码
     * @param phone 手机号
     * @return
     */
    //发送短信的方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {

//        if (StringUtils.isEmpty(phone)) return false;
//
//        //参数1：地域节点
//        //参数2：AccessKey ID
//        //参数3：AccessKey Secret
//        DefaultProfile profile = DefaultProfile.getProfile("default", "密匙id", "密匙密码");
//        DefaultAcsClient client = new DefaultAcsClient(profile);
//
//        //这里不能修改
//        CommonRequest request = new CommonRequest();
//        //request.setProtocol(ProtocolType.HTTPS);
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//
//        request.putQueryParameter("PhoneNumbers", phone);                    //手机号
//        request.putQueryParameter("SignName", "阿里云短信测试");    //申请阿里云 签名名称（暂时用阿里云测试的，自己还不能注册签名）
//        request.putQueryParameter("TemplateCode", "SMS_284910636"); //申请阿里云 模板code（用的也是阿里云测试的）
//        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
//
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            return response.getHttpResponse().isSuccess();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return false;
    }
}


