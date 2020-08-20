package org.project.modules.message.entity;

import org.project.common.system.base.entity.BaseEntity;

import java.util.Date;

/**
 * taskstimate
 */
public class ZtTaskestimateEntity extends BaseEntity {

    private String task;
    private Date date;
    private String left;
    private Double consumed;
    private String account;
    private String work;
    private String userName;
    private String beginTime;
    private String endTime;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public Double getConsumed() {
        return consumed;
    }

    public void setConsumed(Double consumed) {
        this.consumed = consumed;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ZtTaskestimateEntity{" +
                "task='" + task + '\'' +
                ", date=" + date +
                ", left='" + left + '\'' +
                ", consumed=" + consumed +
                ", account='" + account + '\'' +
                ", work='" + work + '\'' +
                ", userName='" + userName + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                "} " + super.toString();
    }
}
