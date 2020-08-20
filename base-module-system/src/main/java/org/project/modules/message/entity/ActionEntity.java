package org.project.modules.message.entity;

import java.util.Date;

/**
 * 动作
 */
public class ActionEntity {
    private String id;
    private String objectType;
    private int objectID;
    private String product;
    private int project;
    private String actor;
    private String action;
    private Date date;
    private String comment;
    private String extra;
    private String read;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "ActionEntity{" +
                "id='" + id + '\'' +
                ", objectType='" + objectType + '\'' +
                ", objectID=" + objectID +
                ", product='" + product + '\'' +
                ", project=" + project +
                ", actor='" + actor + '\'' +
                ", action='" + action + '\'' +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                ", extra='" + extra + '\'' +
                ", read='" + read + '\'' +
                '}';
    }
}
