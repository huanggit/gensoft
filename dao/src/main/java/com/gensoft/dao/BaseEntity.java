package com.gensoft.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by alan on 16-5-23.
 */
@MappedSuperclass
public abstract class BaseEntity {
    /**状态有效*/
    public static final Integer STATUS_VALID = 1;
    /**状态无效*/
    public static final Integer STATUS_NO_VALID = 0;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    //0:无效 1:有效
    @JsonIgnore
    protected Integer status=1;
    //创建信息
    @JsonIgnore
    protected Long createById;
    @JsonIgnore
    protected Date createDate;
    @JsonIgnore
    protected Long updateById;
    @JsonIgnore
    protected Date updateDate;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Long getCreateById() {
        return createById;
    }
    public void setCreateById(Long createById) {
        this.createById = createById;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Long getUpdateById() {
        return updateById;
    }
    public void setUpdateById(Long updateById) {
        this.updateById = updateById;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
