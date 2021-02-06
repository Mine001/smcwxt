package com.hulqframe.cwgl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
@Table(name = "p_opened_invoice")
public class OpenedInvoice implements Serializable {

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
        * 金额
        */
        @Column(name = "money")
        @Setter @Getter
        private BigDecimal money;

        /**
        * 备注
        */
        @Column(name = "remark")
        @Setter @Getter
        private String remark;

        /**
        * 纳税人名称
        */
        @Column(name = "taxpayer")
        @Setter @Getter
        private String taxpayer;

        /**
        * 纳税人识别号
        */
        @Column(name = "tax_no")
        @Setter @Getter
        private String taxNo;

        /**
        * 地址
        */
        @Column(name = "address")
        @Setter @Getter
        private String address;

        /**
        * 开户行
        */
        @Column(name = "open_bank")
        @Setter @Getter
        private String openBank;

        /**
        * 服务名称
        */
        @Column(name = "server_name")
        @Setter @Getter
        private String serverName;

        /**
        * 规格型号
        */
        @Column(name = "specs")
        @Setter @Getter
        private String specs;

        /**
        * 单位
        */
        @Column(name = "unit")
        @Setter @Getter
        private String unit;

        /**
        * 数量
        */
        @Column(name = "count")
        @Setter @Getter
        private Integer count;

        /**
        * 发票代码
        */
        @Column(name = "invoice_code")
        @Setter @Getter
        private String invoiceCode;

        /**
        * 发票号码
        */
        @Column(name = "invoice_no")
        @Setter @Getter
        private String invoiceNo;

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
        * 开票日期
        */
        @Column(name = "open_time")
        @Setter @Getter
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date openTime;

        /**
        * 状态 0：未收款 1：已收款
        */
        @Column(name = "staus")
        @Setter @Getter
        private String staus;


}