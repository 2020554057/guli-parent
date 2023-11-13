package com.atguigu.eduservice.client;

import com.atguigu.eduservice.entity.ucenter.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)//远程调用的服务名称
public interface UcenterClient {
 
    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    public UcenterMember getMemberInfoById(@PathVariable String memberId);
 
}