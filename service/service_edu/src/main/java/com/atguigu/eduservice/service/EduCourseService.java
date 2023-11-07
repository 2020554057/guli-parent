package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-01
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程
    public String addCourse(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    public CourseInfoVo getCourseInfoById(String courseId);

    //修改课程基本信息
    public void updateCourseInfo(CourseInfoVo courseInfoVo);

    //课程最终发布
    public CoursePublishVo getPublishCourseInfo(String id);

    //删除课程
    public void deleteCourse(String courseId);

    //查询首页热门课程集合
    List<EduCourse> indexCourse();
}
