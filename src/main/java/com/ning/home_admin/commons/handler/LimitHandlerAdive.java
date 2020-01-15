package com.ning.home_admin.commons.handler;

import com.ning.home_admin.commons.exception.LimitAccessException;
import com.ning.home_admin.commons.utils.GeneralResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class LimitHandlerAdive {

    @ExceptionHandler(value = LimitAccessException.class)
    public GeneralResultInfo erro_405(LimitAccessException e){
        log.debug("LimitAccessException",e);
        return new GeneralResultInfo().error().message(e.getMessage());
    }
}
