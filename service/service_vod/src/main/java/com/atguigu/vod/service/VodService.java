package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName:VodService
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/5 17:30
 * @Version 1.0
 */
public interface VodService {

    //传视频到阿里云
    public String uploadVideo(MultipartFile file);

    //删除阿里云上的视频（批量）
    public void deleteBatchVideo(List videoIdList);
}
