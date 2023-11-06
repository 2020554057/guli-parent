package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName:VodFileDegradeFeignClient
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/6 11:20
 * @Version 1.0
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了！");
    }

    @Override
    public R deleteBatchVideo(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了！");
    }
}
