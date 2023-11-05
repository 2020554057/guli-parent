package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

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
}
