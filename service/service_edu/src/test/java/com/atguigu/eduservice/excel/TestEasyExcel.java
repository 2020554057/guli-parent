package com.atguigu.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:TestEasyExcel
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/10/31 16:03
 * @Version 1.0
 */
public class TestEasyExcel {
    public static void main(String[] args) {
/*        //实现excel写的操作
        //1、设置写入文件夹地址和excel文件名称
        String filename = "D:\\write.xlsx";

        //2、调用esayExcel里面的方法实现写操作
        //两个参数：文件名名称 实体类
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());*/


        //实现excel读的操作
        String filename = "D:\\write.xlsx";
        EasyExcel.read(filename, DemoData.class,new ExcelListener()).sheet().doRead();

    }

    //创建方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0;i < 10; i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("jack"+i);
            list.add(data);
        }
        return list;
    }
}
