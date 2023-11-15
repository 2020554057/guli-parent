package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-10-31
 */
@Api(description="课程分类管理")
@RestController
@RequestMapping("/eduservice/edusubject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件读取出来
    @ApiOperation("上传excel添加课程分类")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file,EduSubjectService subjectService){
        //上传过来的excel文件
        eduSubjectService.save(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @ApiOperation("嵌套数据列表")
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        //list集合泛型是一级分类，因为一级分类有他本身和二级分类的集合
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

