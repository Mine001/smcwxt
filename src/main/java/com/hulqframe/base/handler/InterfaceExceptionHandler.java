package com.hulqframe.base.handler;

import com.hulqframe.base.exception.AuthException;
import com.hulqframe.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@ControllerAdvice
public class InterfaceExceptionHandler {
    /**
     * 认证异常
     */
    @ResponseBody
    @ExceptionHandler(AuthException.class)
    public Result businessInterfaceException(AuthException e) {
        Result result=Result.FAILD;
        result.setMessage(e.getTxt());
        result.setCode(e.getCode());
        return result;
    }

    /**
     * 拦截所有运行时的全局异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result runtimeException(RuntimeException e) {
        e.printStackTrace();
        Result result=Result.FAILD;
        result.setMessage("系统运行异常，请联系管理员");
        return result;
    }

    /**
     * 系统异常捕获处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        e.printStackTrace();
        Result result=Result.FAILD;
        result.setMessage("系统错误，请联系管理员");
        return result;
    }
}
