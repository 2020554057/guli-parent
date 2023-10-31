package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-10-31
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    public void save(MultipartFile file,EduSubjectService subjectService);
}
