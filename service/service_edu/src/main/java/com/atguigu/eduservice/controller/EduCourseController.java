package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.TeacherQueryVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //课程列表
    //TODO 完善条件查询带分页
    @ApiOperation(value = "根据条件查询课程分页信息")
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit,
                                 @RequestBody(required = false) CourseQueryVo courseQueryVo){
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        //创建条件构造器
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        if (courseQueryVo != null){
            //获取前端传过来的条件信息
            String title = courseQueryVo.getTitle();
            String teacherId = courseQueryVo.getTeacherId();
            String subjectId = courseQueryVo.getSubjectId();
            String subjectParentId = courseQueryVo.getSubjectParentId();
            String status = courseQueryVo.getStatus();

            //添加查询条件
            if(!StringUtils.isEmpty(title)){
                queryWrapper.like("title",title);
            }
            if(!StringUtils.isEmpty(teacherId)){
                queryWrapper.eq("teacher_id",teacherId);
            }
            if(!StringUtils.isEmpty(status)){
                queryWrapper.eq("status",status);
            }
            if(!StringUtils.isEmpty(subjectId)){
                queryWrapper.eq("subject_id", subjectId);
            }
            if(!StringUtils.isEmpty(subjectParentId)){
                queryWrapper.eq("subject_parent_id", subjectParentId);
            }
        }

        //按添加的时间先后排序
        queryWrapper.orderByDesc("gmt_create");

        //把条件构造器传入，进行分页
        courseService.page(pageCourse,queryWrapper);
        //总记录数
        long total = pageCourse.getTotal();
        //课程信息集合
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }


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

    //删除课程
    @ApiOperation(value = "删除课程")
    @DeleteMapping("/deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.deleteCourse(courseId);
        return R.ok();
    }

}

