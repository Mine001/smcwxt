package com.hulqframe.config.shiro;

import lombok.Getter;
import lombok.Setter;

//登录类型
//普通用户登录，管理员登录
public enum LoginType {
    MEMBER("Member"),  ADMIN("Admin");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
}