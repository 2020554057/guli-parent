package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-13
 */
public interface PayLogService extends IService<PayLog> {

    //生成微信二维码接口
    public Map createNatvie(String orderNo);

    //根据订单号查询订单支付状态
    public Map<String, String> queryPayStatus(String orderNo);

    //向支付表中添加记录，更新订单状态
    public void updateOrdersStatus(Map<String, String> map);
}
