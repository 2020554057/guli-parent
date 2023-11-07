package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:IndexFrontController
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/7 10:38
 * @Version 1.0
 */
@Api(description = "前台显示课程和老师")
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "前台查询课程和老师")
    //查询前8条课程，查询前4条老师
    @GetMapping("/index")
    public R index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduTeacher> teacherList = teacherService.list(queryWrapper);

        //返回数据
        return R.ok().data("courseList",courseList).data("teacherList",courseList);
    }

}
