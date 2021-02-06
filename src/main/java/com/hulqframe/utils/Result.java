package com.hulqframe.utils;

import lombok.Getter;
import lombok.Setter;

public class Result {
    public static Result SUCCESS=new Result("0",true,"",null);
    public static Result FAILD=new Result("-1",false,"",null);
    @Setter @Getter
    private String code="0";
    @Setter @Getter
    private boolean success;
    @Setter @Getter
    private String message;
    @Setter @Getter
    private Object data;

    public Result(String code, boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
