package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-10-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void save(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //parent_id为0：一级分类，不为0：二级分类
        //1、查询所有一级分类
        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapper1);
        //this.list(queryWrapper1);

        //2、查询所有二级分类
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id","0");
        List<EduSubject> twoSubjectList = this.list(queryWrapper2);

        //3、封装一级分类
        //创建list集合，用于存储最终数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //得到oneSubjectList的每一个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);

            //把eduSubject里面值获取出来，放到OneSubject对象里面
            //多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());//复杂的方法
            //oneSubject.setTitle(eduSubject.getTitle());

            //简化方式，Spring中的工具类
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每一个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历二级分类list集合
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject tSubject = twoSubjectList.get(j);
                if (tSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            //4、封装二级分类
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}
