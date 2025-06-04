package com.qingqing.admin.controller;

import com.qingqing.common.constants.MessageConstant;
import com.qingqing.common.exception.BaseException;
import com.qingqing.common.vo.JsonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public JsonVO<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return JsonVO.fail(ex.getMessage());
    }
    /**
     * 处理sql异常
     */
    @ExceptionHandler
    public JsonVO<String> exceptionHandler(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return JsonVO.fail(msg);
        }
        else {
            return JsonVO.fail(MessageConstant.UNKNOWN_ERROR);
        }

    }

}
