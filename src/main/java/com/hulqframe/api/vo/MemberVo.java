package com.hulqframe.api.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



import java.util.Date;
@Data
public class MemberVo {
    /**
     * 主键
     */

    private Integer id;

    /**
     * 账号
     */

    private String account;

    /**
     * 邮箱
     */

    private String email;

    /**
     * 生日
     */

    private Date birthday;



    /**
     * 用户昵称
     */

    private String userName;


}
