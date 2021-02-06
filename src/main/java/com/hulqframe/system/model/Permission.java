package com.hulqframe.system.model;

import com.hulqframe.base.common.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "s_permission")
public class Permission implements Serializable  {
    private static final long serialVersionUID = -6878053406541801993L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter @Getter
    private Integer id;
    @Basic
    @Column(name = "name")
    @Setter @Getter
    private String name;
    @Basic
    @Column(name = "type")
    @Setter @Getter
    private String type;
    @Basic
    @Column(name = "url")
    @Setter @Getter
    private String url;
    @Basic
    @Column(name = "action")
    @Setter @Getter
    private String action;
    @Basic
    @Column(name = "seq")
    @Setter @Getter
    private Integer seq;
    @Basic
    @Column(name = "icon")
    @Setter @Getter
    private String icon;
    @Basic
    @Column(name = "is_delete")
    @Setter @Getter
    private Boolean isDelete=false;
    @Basic
    @Column(name = "parent_id")
    @Setter @Getter
    private Integer parentId;

    public String getTypeText(){
        for(Constants.PERMISSION_TYPE permission_type:Constants.PERMISSION_TYPE.values()){
            if(permission_type.getValue().equals(this.getType())){
                return permission_type.getText();
            }
        }
        return "";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(url, that.url) &&
                Objects.equals(action, that.action) &&
                Objects.equals(seq, that.seq) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(isDelete, that.isDelete)&&
                Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, url, action, seq, icon, isDelete,parentId);
    }
}
