package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQueryVo;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-10-24
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin    //解决跨域问题
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查找所有讲师的信息
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);

        //自己制造异常
        try {
            int a = 10/0;
        }catch (Exception e){
            throw new GuliException(20001,"执行了自定义异常处理。。。。");
        }

        return R.ok().data("items",teacherList);
    }

    /**
     * 根据id逻辑删除讲师信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable("id") String id){
        boolean flag = eduTeacherService.removeById(id);
        if (false){
            return R.error();
        }else {
           return R.ok();
        }
    }

    /**
     * 讲师分页查询接口
     * @param current   当前页
     * @param limit     每页显示数
     * @return
     */
    @ApiOperation(value = "分页查询讲师信息")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit){
        //1、创建Page对象
        Page<EduTeacher> pageTeacher = new Page(current,limit);

        //2、调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到teacherPage对象里面
        eduTeacherService.page(pageTeacher, null);
        //总记录数
        long total = pageTeacher.getTotal();
        //讲师信息集合
        List<EduTeacher> records = pageTeacher.getRecords();

        //3、返回信息
        //return R.ok().data("total",total).data("rows",records);
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    /**
     * 根据条件对讲师信息进行分页
     * @param current
     * @param limit
     * @param teacherQueryVo    传入的条件对象
     * @return
     */
    @ApiOperation(value = "根据条件对讲师信息进行分页")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQueryVo teacherQueryVo){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //创建条件构造器
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getBegin();
        String end = teacherQueryVo.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create", end);
        }
        //按添加时间先后排序
        queryWrapper.orderByDesc("gmt_create");

        //把条件构造器传入，进行分页
        eduTeacherService.page(pageTeacher,queryWrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //讲师信息集合
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据id查询讲师信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("/getTeacher/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        if (!StringUtils.isEmpty(eduTeacher)){
            return R.ok().data("teacher",eduTeacher);
        }else {
            return R.error().message("未查询该讲师");
        }
    }

    /**
     * 根据id修改讲师信息
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "根据id修改讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacherById(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error().message("修改失败");
        }
    }

}

