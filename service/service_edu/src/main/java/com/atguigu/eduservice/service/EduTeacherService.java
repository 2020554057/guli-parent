package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-10-24
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //查询首页名师集合
    List<EduTeacher> indexTeacher();
}
