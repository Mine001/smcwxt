package com.hulqframe.cwgl.model;

import com.hulqframe.base.asp.MybatisFormula;
import com.hulqframe.base.asp.MybatisJoin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "p_project_payment")
@MybatisJoin
public class ProjectPayment implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 支出类型
        */
        @Column(name = "pay_type")
        @Setter @Getter
        private Integer payType;

        /**
        * 项目ID
        */
        @Column(name = "project_id")
        @Setter @Getter
        private Integer projectId;

        /**
        * 支出金额
        */
        @Column(name = "money")
        @Setter @Getter
        private BigDecimal money;

        /**
        * 支出时间
        */
        @Column(name = "pay_time")
        @Setter @Getter
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date payTime;

        /**
        * 备注
        */
        @Column(name = "remark")
        @Setter @Getter
        private String remark;

        /**
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;

        /**
        * 创建日期
        */
        @Column(name = "create_time")
        @Setter @Getter
        private Date createTime;

        /**
        * 创建人
        */
        @Column(name = "create_user")
        @Setter @Getter
        private Integer createUser;

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
        * 支出来源ID
        */
        @Column(name = "biz_id")
        @Setter @Getter
        private Integer bizId;

        /**
        * 支出来源类型
        */
        @Column(name = "biz_type")
        @Setter @Getter
        private Integer bizType;
        /**
         * 获取支付类型名称
         * */
        @MybatisFormula(selectSql = "select name from s_data_dict where id=?",field = "payType")
        @Setter @Getter
        @Transient
        private String payTypeName;

        /**
         * 获取项目名称
         * */
        @MybatisFormula(selectSql = "select name from p_project where id=?",field = "projectId")
        @Setter @Getter
        @Transient
        private String projectName;

}