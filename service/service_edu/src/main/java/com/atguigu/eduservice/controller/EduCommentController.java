package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.ucenter.UcenterMember;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-13
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin//跨域
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id分页查询课程评论
    @GetMapping("/getCommentPage/{page}/{limit}")
    public R getCommentPage(@PathVariable Long page,@PathVariable Long limit,String courseId){
        Page<EduComment> commentPage = new Page<>(page,limit);
        QueryWrapper<EduComment> queryWrapper =new QueryWrapper<>();
        //判断课程id是否为空
        if (!StringUtils.isEmpty(courseId)){
            queryWrapper.eq("course_id",courseId);
        }

        //按最新顺序排序
        queryWrapper.orderByDesc("gmt_create");

        //数据会封装到commentPage中
        commentService.page(commentPage,queryWrapper);

        List<EduComment> commentList = commentPage.getRecords();
        long current = commentPage.getCurrent();//当前页
        long size = commentPage.getSize();//一页记录数
        long total = commentPage.getTotal();//总记录数
        long pages = commentPage.getPages();//总页数
        boolean hasPrevious = commentPage.hasPrevious();//是否有上页
        boolean hasNext = commentPage.hasNext();//是否有下页

        HashMap<String, Object> map = new HashMap<>();
        map.put("current",current);
        map.put("size",size);
        map.put("total",total);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        map.put("list",commentList);

        return R.ok().data(map);
    }

    //添加评论
    @PostMapping("/auth/addComment")
    public R addComment(HttpServletRequest request, @RequestBody EduComment eduComment){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //判断用户是否登录
        if (StringUtils.isEmpty(memberId)){
            //throw new GuliException(20001,"请先登录");
            return R.error().message("请先登录");
        }
        eduComment.setMemberId(memberId);

        //远程调用ucenter根据用户id获取用户信息
        UcenterMember member = ucenterClient.getMemberInfoById(memberId);

        eduComment.setAvatar(member.getAvatar());
        eduComment.setNickname(member.getNickname());
        eduComment.setGmtCreate(member.getGmtCreate());
        eduComment.setGmtModified(member.getGmtModified());

        //保存评论
        commentService.save(eduComment);

        return R.ok();
    }
}

