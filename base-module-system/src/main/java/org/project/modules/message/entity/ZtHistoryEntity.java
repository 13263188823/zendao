package org.project.modules.message.entity;

import java.util.List;
import java.util.Map;

/**
 * 历史操作信息
 */
public class ZtHistoryEntity {
    private int id;
    private int action;
    private String field;
    private String old;
    //解析时需要对照装载 原字段 new
    private String newInfo;
    private String diff;
    //完成参数
    private Double cpCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getNewInfo() {
        return newInfo;
    }

    public void setNewInfo(String newInfo) {
        this.newInfo = newInfo;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public Double getCpCount() {
        return cpCount;
    }

    public void setCpCount(Double cpCount) {
        this.cpCount = cpCount;
    }

    @Override
    public String toString() {
        return "ZtHistoryEntity{" +
                "id=" + id +
                ", action=" + action +
                ", field='" + field + '\'' +
                ", old='" + old + '\'' +
                ", newInfo='" + newInfo + '\'' +
                ", diff='" + diff + '\'' +
                ", cpCount=" + cpCount +
                '}';
    }
}
