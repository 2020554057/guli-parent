package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-13
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/createOrders/{courseId}")
    public R createOrders(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        R orders = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return orders;
    }

    //根据订单id查询订单信息
    @PostMapping("/getOrdersInfo/{orderId}")
    public R getOrdersInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    //根据课程id和用户id查询课程订单状态
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getCourseId,courseId);
        queryWrapper.eq(Order::getMemberId,memberId);
        queryWrapper.eq(Order::getStatus,1);//状态1：代表已经支付
        long count = orderService.count(queryWrapper);

        if (count>0){
            return true;
        }else {
            return false;
        }
    }

}

