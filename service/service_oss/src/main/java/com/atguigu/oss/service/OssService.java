package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName:OssService
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/10/29 14:54
 * @Version 1.0
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
