package com.hulqframe.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "s_banner")
public class Banner implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 标题
        */
        @Column(name = "title")
        @Setter @Getter
        private String title;

        /**
        * 备注
        */
        @Column(name = "remark")
        @Setter @Getter
        private String remark;

        /**
        * 标题图
        */
        @Column(name = "title_img")
        @Setter @Getter
        private String titleImg;

        /**
        * 外链接
        */
        @Column(name = "out_link")
        @Setter @Getter
        private String outLink;

        /**
        * 类型
        */
        @Column(name = "type")
        @Setter @Getter
        private Integer type;

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
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;

        /**
        * 排序
        */
        @Column(name = "seq")
        @Setter @Getter
        private Integer seq;

        /**
        * 有效期开始时间
        */
        @Column(name = "start_time")
        @Setter @Getter
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startTime;

        /**
        * 有效期结束时间
        */
        @Column(name = "end_time")
        @Setter @Getter
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;


}