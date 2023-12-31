package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.client.OrdersClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName:TeacherFrontController
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/9 10:08
 * @Version 1.0
 */
@Api(description = "前端课程显示管理")
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    
    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    //1、带条件分页查询课程
    @ApiOperation(value = "带条件分页查询课程")
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable Long page, @PathVariable Long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getFrontCourseList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }
    
    //2、查询课程详细的方法
    @ApiOperation(value = "查询课程详细")
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，编写SQL语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        
        //根据课程id，查询章节小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //判断用户是否登录
        if (JwtUtils.getMemberIdByJwtToken(request) == null){
            return R.error().code(28004).message("请先登录");
        }else {
            //远程调用，根据课程id和用户id查询订单是否支付
            Boolean isBuy = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

            //返回数据
            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",isBuy);
        }
    }

    //根据用户id查询课程信息(生成订单调用的方法)
    @ApiOperation(value = "根据用户id查询课程信息")
    @PostMapping("/getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String courseId){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);

        return courseWebVoOrder;
    }
}
