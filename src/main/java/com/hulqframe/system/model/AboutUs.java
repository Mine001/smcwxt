package com.hulqframe.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "s_about_us")
public class AboutUs implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 公司名称
        */
        @Column(name = "company_name")
        @Setter @Getter
        private String companyName;

        /**
        * 公司介绍
        */
        @Column(name = "distribution")
        @Setter @Getter
        private String distribution;

        /**
        * 软件信息
        */
        @Column(name = "soft_message")
        @Setter @Getter
        private String softMessage;

        /**
        * 注册协议
        */
        @Column(name = "register_agreement")
        @Setter @Getter
        private String registerAgreement;

        /**
        * 地址
        */
        @Column(name = "address")
        @Setter @Getter
        private String address;

        /**
        * 联系电话
        */
        @Column(name = "link_tel")
        @Setter @Getter
        private String linkTel;

        /**
        * 联系人
        */
        @Column(name = "link_man")
        @Setter @Getter
        private String linkMan;

        /**
        * 邮箱
        */
        @Column(name = "email")
        @Setter @Getter
        private String email;

        /**
        * 创建时间
        */
        @Column(name = "create_time")
        @Setter @Getter
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;


}