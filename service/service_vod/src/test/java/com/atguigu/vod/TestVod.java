package com.atguigu.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * ClassName:TestVod
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/5 16:28
 * @Version 1.0
 */
public class TestVod {

    /**使用上传的SDK使用流的方式上传视频到阿里云的视频点播服务上
     * @param args
     * @throws ClientException
     */
    public static void main(String[] args) throws ClientException {
        String accessKeyId = "wsjLTAI5t36649qs51545B8cNPUfFLBUZQ";
        String accessKeySecret = "WNstLAbZ424PlQwmi0224B4ZTJ55PRYeyJCnzgwsj";

        String title = "6 - What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
        String fileName = "D:\\MyFiles\\Java学习之路\\6.项目实战\\谷粒学院\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 根据视频id获取视频播放凭证
     * @throws Exception
     */
    public static void getPlayAuth() throws Exception {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("wsjLTAI5t639q436s554351B8cNPUf66FLBUZQ", "WNstLA34bZPlQwmi6460B445ZTJP646RYeyJCnzgwsj");

        //创建获取视频凭证request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //向request对象里面设置视频id
        request.setVideoId("c13fd9107bb871ee804d4531858c0102");
        //调用初始化对象里面的方法，传递request，获取凭证
        response = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }

    /**
     * 根据视频id获取视频播放地址
     *
     * @throws Exception
     */
    public static void getPlayUrl() throws Exception {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("wsjLTAI5好的话t9qs5636hfg1B他545488cN6PU他忽然FLBUZQ", "WNgrgstLAbrgrgZPlgQwmi0BgrTJPgrRYeyJCnzgrgwsj");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();

        //向request对象里面设置视频id
        request.setVideoId("c13fd9107bb871ee804d4531858c0102");

        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

}
