package com.hulqframe.base.exception;
/**
 * 权限认证异常
 * */
public class AuthException extends Exception{
    public enum AuthExceptionEnum{
        NO_TOKEN("NO_TOKEN_EXCEPTION","请先登录","401"),
        TOKEN_TIME_OUT("TOKEN_TIME_OUT_EXCEPTION","token超时","401"),
        INVALID_TOKEN("INVALID_TOKEN_EXCEPTION","无效token","401"),
        NO_PROMISE("NO_PROMISE_EXCEPTION","没有权限","500");
        private String val;
        private String txt;
        private String code;
        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        AuthExceptionEnum(String val, String txt, String code) {
            this.val = val;
            this.txt = txt;
            this.code = code;
        }
        public static AuthExceptionEnum getAuthExceptionEnumBuVal(String val){
            for(AuthExceptionEnum exceptionEnum:AuthExceptionEnum.values()){
                if(exceptionEnum.getVal().equals(val)){
                    return exceptionEnum;
                }
            }
            return AuthExceptionEnum.INVALID_TOKEN;
        }
    }

    private String txt;
    private String val;
    private String code;
    public AuthException(String message) {
        super(message);
        AuthExceptionEnum exceptionEnum=AuthExceptionEnum.getAuthExceptionEnumBuVal(message);
        this.txt=exceptionEnum.getTxt();
        this.code=exceptionEnum.getCode();
        this.val=message;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
