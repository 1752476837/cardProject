package com.ly.cardadmin.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//注解拦截所有的带Controller注解的类（启动类的同级目录以内），做AoP环绕通知
@ControllerAdvice
public class CommonExceptionHandler {

    //注解可以拦截指定异常，并将异常传入方法的参数
    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handleException(LyException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
