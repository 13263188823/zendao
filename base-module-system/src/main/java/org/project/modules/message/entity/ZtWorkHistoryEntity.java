package org.project.modules.message.entity;

import org.project.common.system.base.entity.BaseEntity;

import java.util.Date;

/**
 * 历史操作快查
 */
public class ZtWorkHistoryEntity extends BaseEntity {

    private String name;
    private String memo;
    private String status;
    private String count;
    private String countName;
    private String recordingCount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public String getRecordingCount() {
        return recordingCount;
    }

    public void setRecordingCount(String recordingCount) {
        this.recordingCount = recordingCount;
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
        return "ZtWorkHistoryEntity{" +
                "name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                ", status='" + status + '\'' +
                ", count='" + count + '\'' +
                ", countName='" + countName + '\'' +
                ", recordingCount='" + recordingCount + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", modifiedMan='" + modifiedMan + '\'' +
                ", modifiedDate=" + modifiedDate +
                "} " + super.toString();
    }
}
