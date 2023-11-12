package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 添加课程基本信息的方法
     * @param courseInfoVo
     */
    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {
        //1、向课程表中添加课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0){
            throw new GuliException(20001,"添加课程信息失败");
        }
        //获取课程表的id与课程描述建立联系
        String cid = eduCourse.getId();

        //2、向课程描述表中添加课程的简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(eduCourseDescription);

        //3、返回课程id
        return cid;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        //1、查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2、查询课程描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程基本信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1、修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        //2、修改课程描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    //课程最终发布
    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        //调用mapper(自己写的SQL)
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void deleteCourse(String courseId) {
        //1、根据课程id删除小节
        eduVideoService.deleteVideoByCourseId(courseId);

        //2、根据课程id删除章节
        eduChapterService.deleteChapterByCourseId(courseId);

        //3、根据课程id删除课程描述
        courseDescriptionService.removeById(courseId);

        //4、根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);

        //5、判断是否删除
        if (result == 0){
            throw new GuliException(20001,"删除失败！");
        }
    }

    //查询首页热门课程集合
    @Cacheable(value = "courseList",key = "'selectIndexCourseList'")//添加redis缓存
    @Override
    public List<EduCourse> indexCourse() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    //带条件分页查询课程
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //创建条件构造器
        LambdaQueryWrapper<EduCourse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            lambdaQueryWrapper.eq(EduCourse::getSubjectParentId,courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){//二级分类
            lambdaQueryWrapper.eq(EduCourse::getSubjectId,courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//关注度
            lambdaQueryWrapper.orderByDesc(EduCourse::getBuyCount);
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){//最新
            lambdaQueryWrapper.orderByDesc(EduCourse::getGmtCreate);
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){//价格
            lambdaQueryWrapper.orderByDesc(EduCourse::getPrice);
        }

        //查询分页信息并封装
        baseMapper.selectPage(pageParam,lambdaQueryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        //map返回
        return map;
    }

    //查询课程详细的方法
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
