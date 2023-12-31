package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.MD5;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UcenterMemberMapper ucenterMemberMapper;

    //用户登录
    @Override
    public R login(UcenterMember member) {
        //获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //判断手机号和密码非空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            //throw new GuliException(20001,"登录失败");
            return R.error().message("账号或密码不能为空！");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);
        if (mobileMember == null){
            //throw new GuliException(20001,"登录失败");
            return R.error().message("账号不存在！");
        }

        //判断密码是否正确
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            //throw new GuliException(20001,"登录失败");
            return R.error().message("密码错误！");
        }

        //判断用户是否禁用
        if (mobileMember.getIsDisabled()){
            //throw new GuliException(20001,"登录失败");
            return R.error().message("该账号已被禁用！");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return R.ok().data("token",jwtToken);
    }

    //用户注册
    @Override
    public R register(RegisterVo registerVo) {
        //获取前端用户注册的数据
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();

        //判断注册信息非空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            //throw new GuliException(20001,"注册失败");
            return R.error().message("注册信息不能为空！");
        }

        //判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            //throw new GuliException(20001,"注册失败");
            return R.error().message("验证码错误！");
        }

        //判断手机号是否存在
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Long count = baseMapper.selectCount(queryWrapper);
        if (count>0){
            //throw new GuliException(20001,"注册失败");
            return R.error().message("账号已存在！");
        }

        //数据库中添加注册信息
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));//密码要使用MD5加密
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);//用户不禁用
        ucenterMember.setAvatar("https://edu-guli-wsj.oss-cn-beijing.aliyuncs.com/2023/10/29/b98237cc5af44537a26d27c38cb1a827file.png");
        baseMapper.insert(ucenterMember);

        //注册成功返回
        return R.ok().message("注册成功！");
    }

    //根据openid查询，判断微信用户是否存在
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    //根据日期查询注册人数
    @Override
    public Integer countRegister(String day) {
        Integer count = ucenterMemberMapper.countRegister(day);
        return count;
    }
}
