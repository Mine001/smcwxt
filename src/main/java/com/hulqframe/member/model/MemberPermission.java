package com.hulqframe.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "m_member_permission")
public class MemberPermission implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 名称
        */
        @Column(name = "name")
        @Setter @Getter
        private String name;

        /**
        * 地址
        */
        @Column(name = "url")
        @Setter @Getter
        private String url;

        /**
        * 类型
        */
        @Column(name = "type")
        @Setter @Getter
        private Integer type;

        /**
        * 排序
        */
        @Column(name = "seq")
        @Setter @Getter
        private Integer seq;

        /**
        * 父级ID
        */
        @Column(name = "parent_id")
        @Setter @Getter
        private Integer parentId;

        /**
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;


}