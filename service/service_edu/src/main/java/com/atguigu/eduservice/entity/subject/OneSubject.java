package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:OneSubject
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/10/31 22:21
 * @Version 1.0
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    //一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
