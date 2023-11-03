package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
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

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping("/getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    /**
     * 修改课程基本信息
     * @param courseInfoVo
     * @return
     */
    @ApiOperation(value = "修改课程基本信息")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "查询课程发布基本信息")
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    @ApiOperation(value = "课程最终发布")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }


}

