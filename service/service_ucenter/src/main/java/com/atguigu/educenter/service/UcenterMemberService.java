package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-07
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //用户登录
    public String login(UcenterMember member);

    //用户注册
    public void register(RegisterVo registerVo);
}