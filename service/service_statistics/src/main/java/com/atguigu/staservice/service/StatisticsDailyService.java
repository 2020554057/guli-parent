package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-14
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //统计某一天注册的人数，生成统计数据
    public void registerCount(String day);

    //图标显示，返回俩个部分数据，日期json数据，数量json数据
    public Map<String, Object> getShowData(String type,String begin,String end);
}
