package com.ning.home_admin.commons.exception;

public class LimitAccessException extends Exception{

    private static final long serialVersionUID = -994962710559017255L;

    public LimitAccessException(String message) {
        super(message);
    }
}
