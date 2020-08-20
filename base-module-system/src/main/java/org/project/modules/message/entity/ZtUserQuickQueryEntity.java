package org.project.modules.message.entity;

import org.project.common.system.base.entity.BaseEntity;

import java.util.Date;

/**
 * 历史操作快查
 */
public class ZtUserQuickQueryEntity extends BaseEntity {
    private String name;
    private String memo;
    private int count;
    private Date createDate;
    private Date updateDate;
    private String modifiedMan;
    private Date modifiedDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getModifiedMan() {
        return modifiedMan;
    }

    public void setModifiedMan(String modifiedMan) {
        this.modifiedMan = modifiedMan;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "ZtUserQuickQueryEntity{" +
                "name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                ", count=" + count +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", modifiedMan='" + modifiedMan + '\'' +
                ", modifiedDate=" + modifiedDate +
                "} " + super.toString();
    }
}
