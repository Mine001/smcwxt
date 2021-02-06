package com.hulqframe.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Objects;

@Entity
@Table(name = "s_org")
public class Org  implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter @Getter public Integer id;
    @Basic
    @Column(name = "org_name")
    @Setter @Getter public String orgName;
    @Basic
    @Column(name = "org_code")
    @Setter @Getter public String orgCode;
    @Basic
    @Column(name = "parent_id")
    @Setter @Getter public Integer parentId;
    @Basic
    @Column(name = "seq")
    @Setter @Getter public Integer seq=0;
    @Basic
    @Column(name = "remark")
    @Setter @Getter public String remark;
    @Basic
    @Column(name = "is_delete")
    @Setter @Getter public Boolean isDelete=false;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Org org = (Org) o;
        return id == org.id &&
                Objects.equals(orgName, org.orgName) &&
                Objects.equals(orgCode, org.orgCode) &&
                Objects.equals(parentId, org.parentId) &&
                Objects.equals(seq, org.seq) &&
                Objects.equals(remark, org.remark) &&
                Objects.equals(isDelete, org.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orgName, orgCode, parentId, seq, remark, isDelete);
    }
}
