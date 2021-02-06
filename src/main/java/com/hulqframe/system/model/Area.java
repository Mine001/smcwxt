package com.hulqframe.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "s_area")
public class Area implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 是否删除
        */
        @Column(name = "is_delete")
        @Setter @Getter
        private Boolean isDelete;

        /**
        * 地区编码
        */
        @Column(name = "code")
        @Setter @Getter
        private String code;

        /**
        * 名称
        */
        @Column(name = "name")
        @Setter @Getter
        private String name;

        /**
        * 级别
        */
        @Column(name = "level")
        @Setter @Getter
        private Integer level;

        /**
        * 城市编码
        */
        @Column(name = "city_code")
        @Setter @Getter
        private String cityCode;

        /**
        * 中心点坐标
        */
        @Column(name = "center")
        @Setter @Getter
        private String center;

        /**
        * 父节点ID
        */
        @Column(name = "parent_Id")
        @Setter @Getter
        private Integer parentId;

        @Column(name="have_child")
        @Setter @Getter
        private Boolean haveChild;
}