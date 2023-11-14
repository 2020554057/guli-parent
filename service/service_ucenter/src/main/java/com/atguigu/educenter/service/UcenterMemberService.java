package com.atguigu.educenter.service;

import com.atguigu.commonutils.R;
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
    public R login(UcenterMember member);

    //用户注册
    public R register(RegisterVo registerVo);

    //判断微信用户是否存在
    public UcenterMember getOpenIdMember(String openid);

    //根据日期查询注册人数
    public Integer countRegister(String day);
}
