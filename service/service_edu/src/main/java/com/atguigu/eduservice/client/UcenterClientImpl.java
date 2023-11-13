package com.atguigu.eduservice.client;

import com.atguigu.eduservice.entity.ucenter.UcenterMember;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public UcenterMember getMemberInfoById(String memberId) {
        return null;
    }
}