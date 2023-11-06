package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-01
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;//注入远程调用的接口

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //TODO 后面这个方法还需要完善，删除小节的同时删除小节中的视频
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        //1、先删除视频
        //根据小节id获取视频id，调用方法实现视频的删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节中是否有视频
        if (!StringUtils.isEmpty(videoSourceId)){
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }
        }

        //2、再删除小节
        videoService.removeById(id);
        return R.ok();
    }

    //TODO：修改小节

}

