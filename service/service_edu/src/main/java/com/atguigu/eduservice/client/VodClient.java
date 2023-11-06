package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ClassName:VodClient
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/6 9:35
 * @Version 1.0
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)//远程调用的服务名称
@Component
public interface VodClient {

    //远程调用service-vod服务删除视频
    //1、url路径一定要写全
    //2、PathVariable一定要写路径变量名称，否则会报错
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    //批量删除视频
    @DeleteMapping("/eduvod/video//deleteBatchVideo")
    public R deleteBatchVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
