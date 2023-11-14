package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.RandomUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-14
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        //添加记录之前删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        //远程调用获取某一天注册人数
        R count = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) count.getData().get("count");

        //把获取的数据添加到数据库中，统计分析表里面
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); //注册人数
        sta.setDateCalculated(day); //统计日期

        //TODO
        sta.setCourseNum(RandomUtils.nextInt(100, 200));//新增课程数
        sta.setLoginNum(RandomUtils.nextInt(200, 300));//登录数
        sta.setVideoViewNum(RandomUtils.nextInt(200, 300));//视频流量数

        baseMapper.insert(sta);
    }

    //图标显示，返回俩个部分数据，日期json数据，数量json数据
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date_calculated", type);
        queryWrapper.between("date_calculated", begin, end);
        List<StatisticsDaily> staList = baseMapper.selectList(queryWrapper);

        //因为返回有两部分数据：日期 和 日期对应的数量
        //前端要求数组json结构，对应后端java代码是list集合
        //创建两个list集合，一个是日期list，一个数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //遍历查询所有数据list集合，进行拼接
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            //封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());

            //根据传递的类型封装数量list集合
            switch (type) {
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        // 把封装之后两个list集合放到map集合，进行返回
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);
        return map;
    }
}
