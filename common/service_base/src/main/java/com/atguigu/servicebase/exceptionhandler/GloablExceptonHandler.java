package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@Slf4j
@ControllerAdvice
public class GloablExceptonHandler {

  //指定出现什么异常执行这个方法
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public R error(Exception e){
      e.printStackTrace();
      return R.error().message("这里是全局异常处理，异常信息："+e.getMessage());
   }

   //特定的异常处理，如果不是特定的异常，就会走全局异常处理（Exception）
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("特定异常处理");
    }

    //自定义异常处理（使用了自定义异常处理类）
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
       //@slf4j的日志方法，这里是将日志信息输出到外置的log_error.log文件中
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
