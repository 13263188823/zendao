package org.project.modules.message.entity;

import org.project.common.system.base.entity.BaseEntity;

import java.util.Date;

/**
 * taskstimate
 */
public class ZtTaskestimateeEntity extends BaseEntity {

    private int task;
    private Date date;
    private float left;
    private float consumed;
    private String account;
    private String work;
    private String userName;
    private String beginTime;
    private String endTime;

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getConsumed() {
        return consumed;
    }

    public void setConsumed(float consumed) {
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
        return "ZtTaskestimateeEntity{" +
                "task='" + task + '\'' +
                ", date=" + date +
                ", left=" + left +
                ", consumed=" + consumed +
                ", account='" + account + '\'' +
                ", work='" + work + '\'' +
                ", userName='" + userName + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                "} " + super.toString();
    }
}
