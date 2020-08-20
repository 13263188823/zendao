package org.project.modules.message.entity;

/**
 * 用户扩展类
 */
public class ExpandUserEntity extends UserEntity{
    private Double consumed;

    public Double getConsumed() {
        return consumed;
    }

    public void setConsumed(Double consumed) {
        this.consumed = consumed;
    }

    @Override
    public String toString() {
        return "ExpandUserEntity{" +
                "consumed=" + consumed +
                "} " + super.toString();
    }
}
