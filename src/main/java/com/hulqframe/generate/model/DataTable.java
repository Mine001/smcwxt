package com.hulqframe.generate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "g_table")
public class DataTable implements Serializable {
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
     * 表名称
     */
    @Column(name = "table_name")
    @Setter @Getter
    private String tableName;


    /**
     * 字段描述
     */
    @Setter @Getter
    @Column(name = "des")
    private String des;

    /**
     * 状态
     */
    @Setter @Getter
    private Boolean isDelete=false;

    private static final long serialVersionUID = 1L;

}