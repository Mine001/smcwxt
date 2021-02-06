package com.hulqframe.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "m_member_role")
public class MemberRole implements Serializable {

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
        * 描述信息
        */
        @Column(name = "description")
        @Setter @Getter
        private String description;

        /**
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;


}