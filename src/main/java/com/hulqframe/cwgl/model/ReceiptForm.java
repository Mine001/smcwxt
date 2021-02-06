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

@Table(name = "p_receipt_form")
@MybatisJoin
public class ReceiptForm implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 项目ID
        */
        @Column(name = "project_id")
        @Setter @Getter
        private Integer projectId;

        /**
        * 供应商ID
        */
        @Column(name = "supplier_id")
        @Setter @Getter
        private Integer supplierId;

        /**
        * 签收日期
        */
        @Column(name = "receipt_time")
        @Setter @Getter
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date receiptTime;

        /**
        * 签收内容
        */
        @Column(name = "receipt_content")
        @Setter @Getter
        private String receiptContent;

        /**
        * 总金额
        */
        @Column(name = "money")
        @Setter @Getter
        private BigDecimal money;

        /**
        * 状态0:代付款、1:已付款
        */
        @Column(name = "status")
        @Setter @Getter
        private String status;

        /**
        * 创建时间
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
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;

        /**
        * 备注
        */
        @Column(name = "remark")
        @Setter @Getter
        private String remark;

        /**
         * 获取项目名称
         * */
        @MybatisFormula(selectSql = "select name from p_project where id=?",field = "projectId")
        @Setter @Getter
        @Transient
        private String projectName;

        /**
         * 获取供应商名称
         * */
        @MybatisFormula(selectSql = "select name from p_supplier where id=?",field = "supplierId")
        @Setter @Getter
        @Transient
        private String supplierName;

        /**
         * 获取已付款金额
         * */
        @MybatisFormula(selectSql = "select sum(money) from p_receipt_payment where receipt_id=?",field = "id")
        @Setter @Getter
        @Transient
        private BigDecimal hasPay;
}