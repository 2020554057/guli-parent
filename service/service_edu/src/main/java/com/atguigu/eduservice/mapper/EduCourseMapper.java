package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-01
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程发布确认信息
    public CoursePublishVo getPublishCourseInfo(String courseId);
}
