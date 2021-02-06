package com.hulqframe.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "s_data_dict")
public class DataDict implements Serializable {

        /**
        * 
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
        * 编码
        */
        @Column(name = "code")
        @Setter @Getter
        private String code;

        /**
        * 上级
        */
        @Column(name = "parent_id")
        @Setter @Getter
        private Integer parentId;

        /**
        * 排序
        */
        @Column(name = "seq")
        @Setter @Getter
        private Integer seq;

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
        * 层级
        */
        @Column(name = "level")
        @Setter @Getter
        private Integer level;


}