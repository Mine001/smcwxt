package com.hulqframe.cwgl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "p_project")
public class Project implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 项目名称
        */
        @Column(name = "name")
        @Setter @Getter
        private String name;

        /**
        * 编码
        */
        @Column(name = "code")
        @Setter @Getter
        private String code;

        /**
        * 联系人
        */
        @Column(name = "link_man")
        @Setter @Getter
        private String linkMan;

        /**
        * 联系人电话
        */
        @Column(name = "link_man_tel")
        @Setter @Getter
        private String linkManTel;

        /**
        * 项目地址
        */
        @Column(name = "address")
        @Setter @Getter
        private String address;

        /**
        * 项目所在区域
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
        * 项目金额
        */
        @Column(name = "money")
        @Setter @Getter
        private Double money;

        /**
        * 备注
        */
        @Column(name = "remark")
        @Setter @Getter
        private String remark;

        /**
        * 施工队
        */
        @Column(name = "construction")
        @Setter @Getter
        private String construction;

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
        * 项目状态
        */
        @Column(name = "status")
        @Setter @Getter
        private String status;

        /**
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;


}