package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-01
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return
     */
    @ApiOperation(value = "添加课程信息")
    @PostMapping("/addCourseInfo")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.addCourse(courseInfoVo);
        return R.ok().data("courseId",courseId);
    }
}

