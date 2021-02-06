package com.hulqframe.cwgl.model;

import com.hulqframe.base.asp.MybatisFormula;
import com.hulqframe.base.asp.MybatisJoin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
@Table(name = "p_payment")
@MybatisJoin
public class Payment implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 供应商ID
        */
        @Column(name = "supplier_id")
        @Setter @Getter
        private Integer supplierId;

        /**
        * 付款时间
        */
        @Column(name = "pay_time")
        @Setter @Getter
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date payTime;

        /**
        * 付款金额
        */
        @Column(name = "pay_money")
        @Setter @Getter
        private BigDecimal payMoney;

        /**
        * 付款内容
        */
        @Column(name = "pay_content")
        @Setter @Getter
        private String payContent;

        /**
        * 付款类型
        */
        @Column(name = "pay_type")
        @Setter @Getter
        private String payType;

        /**
        * 收款方
        */
        @Column(name = "receiver")
        @Setter @Getter
        private String receiver;

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
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;

        /**
        * 状态：0：待审核  1:已审核
        */
        @Column(name = "status")
        @Setter @Getter
        private String status;

        /**
        * 发票ID
        */
        @Column(name = "invoice_id")
        @Setter @Getter
        private Integer invoiceId;


        @MybatisFormula(selectSql = "select name from p_supplier where id=?",field = "supplierId")
        @Setter @Getter
        @Transient
        private String supplierName;
        /**
         * 获取支付类型名称
         * */
        @MybatisFormula(selectSql = "select name from s_data_dict where id=?",field = "payType")
        @Setter @Getter
        @Transient
        private String payTypeName;

}