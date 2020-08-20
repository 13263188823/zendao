package org.project.modules.message.entity;

import org.project.common.system.base.entity.BaseEntity;

import java.util.Date;

/**
 * 任务
 */
public class TaskEntity extends BaseEntity {
    private String id;
    private String name;
    private Double consumed;
    private String finishedBy;
    private Long extra;
    private String actor;
    private String action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getConsumed() {
        return consumed;
    }

    public void setConsumed(Double consumed) {
        this.consumed = consumed;
    }

    public String getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(String finishedBy) {
        this.finishedBy = finishedBy;
    }

    public Long getExtra() {
        return extra;
    }

    public void setExtra(Long extra) {
        this.extra = extra;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", consumed=" + consumed +
                ", finishedBy='" + finishedBy + '\'' +
                ", extra=" + extra +
                ", actor='" + actor + '\'' +
                ", action='" + action + '\'' +
                "} " + super.toString();
    }
}
