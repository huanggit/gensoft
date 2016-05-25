package com.gensoft.dao.usergroups;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-25.
 */
@Entity
@Table(name = "user_group_tags")
public class UserGroupTag {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;
    //0:无效 1:有效
    @NotNull
    protected int status;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
