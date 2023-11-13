package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ClassName:UcenterClient
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/13 22:15
 * @Version 1.0
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据用户id查询用户信息(生成订单调用的方法)
    @PostMapping("/educenter/member/getMemberInfoOrder/{memberId}")
    public UcenterMemberOrder getMemberInfoOrder(@PathVariable("memberId") String memberId);
}
