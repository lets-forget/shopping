package com.ning.home_admin.commons.handler;

import com.ning.home_admin.commons.exception.LoginException;
import com.ning.home_admin.commons.utils.GeneralResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GeneralException {

    @ExceptionHandler(LoginException.class)
    public GeneralResultInfo loginException(LoginException e){
        log.error("登录失败",e);
        return new GeneralResultInfo().message(e.getMessage()).error();
    }
}
