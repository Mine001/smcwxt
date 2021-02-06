package com.hulqframe.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "s_role")
public class Role implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter @Getter
    private Integer id;
    @Basic
    @Column(name = "role_name")
    @Setter @Getter
    private String roleName;
    @Basic
    @Column(name = "remark")
    @Setter @Getter
    private String remark;
    @Basic
    @Column(name = "is_delete")
    @Setter @Getter
    private Boolean isDelete=false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id &&
                Objects.equals(roleName, role.roleName) &&
                Objects.equals(remark, role.remark) &&
                Objects.equals(isDelete, role.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, remark, isDelete);
    }
}
