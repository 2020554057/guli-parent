package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * ClassName:SubjectExcelListener
 * Package:IntelliJ IDEA
 * Description:
 *      EasyExcel的读取监听类
 * @Author 吴苏杰
 * @Create 2023/10/31 16:35
 * @Version 1.0
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给Spring管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    //于是使用了有参构造传入值的方式
    public EduSubjectService subjectService;
    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //一行一行的进行读取Excel的内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //1、判断Excel中是否有值
        if (subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }

        //2、一行一行读取，每一次读取两个值，第一个值是一级分类，第二个值是二级分类
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null){//没有相同的一级分类 可以添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        //判断二级分类是否重复
        //获取一级分类的id
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if (existTwoSubject == null){//没有相同的二级分类 可以添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());//二级分类名称
            subjectService.save(existTwoSubject);
        }

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(queryWrapper);
        return oneSubject;
    }
    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(queryWrapper);
        return twoSubject;
    }

    //读取完后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
