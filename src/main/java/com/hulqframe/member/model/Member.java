package com.hulqframe.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "m_member")
public class Member implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 账号
        */
        @Column(name = "account")
        @Setter @Getter
        private String account;

        /**
        * 密码
        */
        @Column(name = "password")
        @Setter @Getter
        private String password;

        /**
        * 邮箱
        */
        @Column(name = "email")
        @Setter @Getter
        private String email;

        /**
        * 生日
        */
        @Column(name = "birthday")
        @Setter @Getter
        private Date birthday;

        /**
        * 微信openId
        */
        @Column(name = "openId")
        @Setter @Getter
        private String openId;

        /**
        * 用户昵称
        */
        @Column(name = "userName")
        @Setter @Getter
        private String userName;

        /**
        * 盐值
        */
        @Column(name = "salt")
        @Setter @Getter
        private String salt;


}