package com.hulqframe.cwgl.model;

import com.hulqframe.base.asp.MybatisFormula;
import com.hulqframe.base.asp.MybatisJoin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "p_supplier")
@MybatisJoin
public class Supplier implements Serializable {

        /**
        * id
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 供应商名称
        */
        @Column(name = "name")
        @Setter @Getter
        private String name;

        /**
        * 地址
        */
        @Column(name = "address")
        @Setter @Getter
        private String address;

        /**
        * 区域
        */
        @Column(name = "region")
        @Setter @Getter
        private Integer region;
        /**
         * 项目所在省份
         */
        @Column(name = "province")
        @Setter @Getter
        private Integer province;
        /**
         * 项目所在市
         */
        @Column(name = "city")
        @Setter @Getter
        private Integer city;

        /**
        * 联系人
        */
        @Column(name = "link_man")
        @Setter @Getter
        private String linkMan;

        /**
        * 联系电话
        */
        @Column(name = "link_tel")
        @Setter @Getter
        private String linkTel;

        /**
        * 备注
        */
        @Column(name = "remark")
        @Setter @Getter
        private String remark;

        /**
        * 简称
        */
        @Column(name = "shotr_name")
        @Setter @Getter
        private String shotrName;

        /**
        * 法人
        */
        @Column(name = "legal")
        @Setter @Getter
        private String legal;

        /**
        * 营业执照
        */
        @Column(name = "license")
        @Setter @Getter
        private String license;

        /**
        * 银行账号
        */
        @Column(name = "bank_no")
        @Setter @Getter
        private String bankNo;

        /**
        * 开户行
        */
        @Column(name = "open_bank")
        @Setter @Getter
        private String openBank;

        /**
        * 付款方式
        */
        @Column(name = "pay_type")
        @Setter @Getter
        private String payType;

        /**
        * 邮箱
        */
        @Column(name = "email")
        @Setter @Getter
        private String email;

        /**
        * 创建人
        */
        @Column(name = "create_user")
        @Setter @Getter
        private Integer createUser;

        /**
        * 创建时间
        */
        @Column(name = "create_time")
        @Setter @Getter
        private Date createTime;

        /**
        * 修改人
        */
        @Column(name = "update_user")
        @Setter @Getter
        private Integer updateUser;

        /**
        * 修改时间
        */
        @Column(name = "update_time")
        @Setter @Getter
        private Date updateTime;

        /**
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;

        @Transient
        @MybatisFormula(selectSql = "select getRegionName(?)" ,field = "region")
        @Setter @Getter
        private String regionName;

}