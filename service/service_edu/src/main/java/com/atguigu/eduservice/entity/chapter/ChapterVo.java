package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ChapterVO
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/1 19:36
 * @Version 1.0
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
