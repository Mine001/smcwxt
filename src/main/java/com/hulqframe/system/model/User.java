package com.hulqframe.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "s_user")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter @Getter
    private Integer id;
    @Basic
    @Column(name = "user_name")
    @Setter @Getter
    private String userName;
    @Basic
    @Column(name = "account")
    @Setter @Getter
    private String account;
    @Basic
    @Column(name = "password")
    @Setter @Getter
    private String password;
    @Basic
    @Column(name = "mobile")
    @Setter @Getter
    private String mobile;
    @Basic
    @Column(name = "sex")
    @Setter @Getter
    private String sex;
    @Basic
    @Column(name = "is_delete")
    @Setter @Getter
    private Boolean isDelete=false;
    @Basic
    @Column(name = "remark")
    @Setter @Getter
    private String remark;
    @Basic
    @Column(name = "email")
    @Setter @Getter
    private String email;
    @Basic
    @Column(name = "org_id")
    @Setter @Getter
    private Integer orgId;

    @Column(name = "salt")
    @Setter @Getter
    private String salt;
    @Setter @Getter
    @Transient
    private List<Permission> permissions;
    @Setter @Getter
    @Transient
    private List<Role> roles;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(account, user.account) &&
                Objects.equals(password, user.password) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(isDelete, user.isDelete) &&
                Objects.equals(remark, user.remark) &&
                Objects.equals(email, user.email);
    }
    public String getCredentialsSalt() {
        return account + salt + salt;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, userName, account, password, mobile, sex, isDelete, remark, email);
    }
}
