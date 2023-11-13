package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-13
 */
public interface OrderService extends IService<Order> {

    //创建订单
    public String createOrders(String courseId, String memberIdByJwtToken);
}
