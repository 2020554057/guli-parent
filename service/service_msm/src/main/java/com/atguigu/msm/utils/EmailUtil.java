package com.atguigu.msm.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * ClassName:EmailUtil
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/8/26 12:56
 * @Version 1.0
 */

public class EmailUtil {
    public static boolean sendAuthCodeEmail(String email, String authCode) {
        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器,这个是qq邮箱的，不用修改
            mail.setAuthentication("2020554057@qq.com", "jwspxsaxbvhedfdh");//第一个参数是对应的邮箱用户名一般就是自己的邮箱第二个参数就是SMTP的密码,我们上面获取过了
            mail.setFrom("2020554057@qq.com","谷粒学院");  //发送邮件的邮箱和发件人
            mail.setSSLOnConnect(false); //使用安全链接
            mail.addTo(email);//接收的邮箱
            mail.setSubject("验证码");//设置邮件的主题
            mail.setMsg("尊敬的用户:你好!\n 登陆验证码为:" + authCode+"\n"+"     (有效期为三分钟)");//设置邮件的内容
            mail.send();//发送
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }
}
