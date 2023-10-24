package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GloablExceptonHandler {

  //指定出现什么异常执行这个方法
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public R error(Exception e){
      e.printStackTrace();
      return R.error().message("这里是全局异常处理，异常信息："+e.getMessage());
   }
}
