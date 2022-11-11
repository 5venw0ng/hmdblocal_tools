package com.ruoyi.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 *

 */
public class BaseEntity extends Entity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 创建者 */

    @TableField(value = "created_by", fill = FieldFill.INSERT) // 新增执行
    private String createdBy;

    /** 创建时间 */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;

    /** 更新者 */

    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    private String updatedBy;

    /** 更新时间 */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "updated_Time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    //暂时不使用逻辑删除
    //@TableLogic
    //private Integer deleted;


    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createBy)
    {
        this.createdBy = createBy;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(Date createTime)
    {
        this.createdTime = createTime;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(String updateBy)
    {
        this.updatedBy = updateBy;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public void setUpdatedTime(Date updateTime)
    {
        this.updatedTime = updateTime;
    }


    /** 适应以前的代码 **/
    @Deprecated
    @JsonIgnore
    public String getCreateBy()
    {
        return createdBy;
    }
    @Deprecated
    @JsonIgnore
    public void setCreateBy(String createBy)
    {
        this.createdBy = createBy;
    }
    @Deprecated
    @JsonIgnore
    public Date getCreateTime()
    {
        return createdTime;
    }
    @Deprecated
    @JsonIgnore
    public void setCreateTime(Date createTime)
    {
        this.createdTime = createTime;
    }
    @Deprecated
    @JsonIgnore
    public String getUpdateBy()
    {
        return updatedBy;
    }
    @Deprecated
    @JsonIgnore
    public void setUpdateBy(String updateBy)
    {
        this.updatedBy = updateBy;
    }
    @Deprecated
    @JsonIgnore
    public Date getUpdateTime()
    {
        return updatedTime;
    }
    @Deprecated
    @JsonIgnore
    public void setUpdateTime(Date updateTime)
    {
        this.updatedTime = updateTime;
    }


    /**
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
     **/
}
