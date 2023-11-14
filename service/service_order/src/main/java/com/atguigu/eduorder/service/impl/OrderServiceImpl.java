package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //创建订单
    @Override
    public R createOrders(String courseId, String memberId) {
        //通过远程调用根据课程id获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);


        //判断用户是否登录
        if (StringUtils.isEmpty(memberId)){
            return R.error().code(28004).message("请先登录");
        }else {
            //通过远程调用根据用户id获取用户信息
            UcenterMemberOrder memberInfoOrder = ucenterClient.getMemberInfoOrder(memberId);

            //创建Order对象，向order对象里面设置需要数据
            Order order = new Order();
            order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
            order.setCourseId(courseId); //课程id
            order.setCourseTitle(courseInfoOrder.getTitle());
            order.setCourseCover(courseInfoOrder.getCover());
            order.setTeacherName(courseInfoOrder.getTeacherName());
            order.setTotalFee(courseInfoOrder.getPrice());
            order.setMemberId(memberId);
            order.setMobile(memberInfoOrder.getMobile());
            order.setNickname(memberInfoOrder.getNickname());
            order.setStatus(0);  //订单状态（0：未支付 1：已支付）
            order.setPayType(1);  //支付类型 ，微信1
            baseMapper.insert(order);
            //return order.getOrderNo();

            //返回订单号
            return R.ok().data("orderId",order.getOrderNo());
        }

    }

}
