package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName:VodController
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/5 17:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
@Api(description = "阿里云视频点播")
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @ApiOperation(value = "上传视频")
    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file){
        //返回上传视频id
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 删除阿里云上的视频（单个）
     * @param id
     * @return
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            //向request设置删除的视频id
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    /**
     * 删除阿里云上的视频（批量）
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/deleteBatchVideo")
    public R deleteBatchVideo(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteBatchVideo(videoIdList);
        return R.ok();
    }

}
