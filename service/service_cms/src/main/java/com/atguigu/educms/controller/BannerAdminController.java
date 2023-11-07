package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner 后台管理控制器
 * </p>
 *
 * @author 吴苏杰
 * @since 2023-11-07
 */
@Api(description = "首页banner 后台管理控制器")
@RestController
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    //分页查询
    @ApiOperation(value = "分页查询Banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable Long page,@PathVariable Long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //添加
    @ApiOperation(value = "添加Banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok().message("添加成功！");
    }

    //根据id删除
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return R.ok().message("删除成功！");
    }

    //根据id修改
    @ApiOperation(value = "修改Banner")
    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok().message("修改成功！");
    }

    //根据id查询
    @ApiOperation(value = "根据id查询Banner")
    @GetMapping("/getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner crmBanner = bannerService.getById(id);
        return R.ok().data("item",crmBanner);
    }

}

