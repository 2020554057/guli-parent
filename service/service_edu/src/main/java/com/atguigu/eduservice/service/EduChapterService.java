package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
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
public interface EduChapterService extends IService<EduChapter> {

    //根据课程Id查询章节和小节
    public List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
