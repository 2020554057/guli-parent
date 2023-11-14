package com.atguigu.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName:OrdersClient
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Author 吴苏杰
 * @Create 2023/11/14 10:27
 * @Version 1.0
 */
@Component
@FeignClient(value = "service-order",fallback = OrdersClientImpl.class)
public interface OrdersClient {

    //根据课程id和用户id查询课程订单状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
