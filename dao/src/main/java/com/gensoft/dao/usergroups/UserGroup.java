package com.gensoft.dao.usergroups;

import com.gensoft.dao.BaseEntity;
import com.gensoft.dao.user.User;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-25.
 */
@Entity
@Table(name = "user_groups")
public class UserGroup extends BaseEntity{

  
    @NotNull
    private long userId;
    @NotNull
    private String name;
    @NotNull
    private String descipt;
    @NotNull
    private long tagId;
    
    

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  

    public String getDescipt() {
		return descipt;
	}

	public void setDescipt(String descipt) {
		this.descipt = descipt;
	}

	public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
