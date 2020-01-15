package com.ning.home_admin.commons.utils;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class GeneralResultInfo extends HashMap<String,Object> {

    public GeneralResultInfo message(String message){
        this.put("message",message);
        return this;
    }

    public GeneralResultInfo code(HttpStatus status){
        this.put("code",status.value());
        return this;
    }

    public GeneralResultInfo success(){
        this.code(HttpStatus.OK);
        return this;
    }

    public GeneralResultInfo error(){
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }
    public GeneralResultInfo data(Object data){
        this.put("data",data);
        return this;
    }
}
