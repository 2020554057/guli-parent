package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
