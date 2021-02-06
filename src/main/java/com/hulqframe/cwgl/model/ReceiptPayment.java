package com.hulqframe.cwgl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
@Table(name = "p_receipt_payment")
public class ReceiptPayment implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 创建日期
        */
        @Column(name = "create_time")
        @Setter @Getter
        private Date createTime;

        /**
        * 金额
        */
        @Column(name = "money")
        @Setter @Getter
        private BigDecimal money;

        /**
        * 签收单ID
        */
        @Column(name = "receipt_id")
        @Setter @Getter
        private Integer receiptId;

        /**
        * 付款ID
        */
        @Column(name = "payment_id")
        @Setter @Getter
        private Integer paymentId;


}