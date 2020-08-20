package org.project.modules.message.util;

/**
 * 部门过滤枚举
 */
public enum ConstantEnum{

    PARENT("0","根节点"),
    WOMAI("10","我买网"),
    PRODUCT("11","生产"),
    DEVELOPMENT("12","开发"),
    TEST("13","测试"),
    OPERATION_AND_MAINTENANCE("14","运维"),
    FRONT_END("15","前端"),
    STAFF_LEAVING("16","离职人员"),
    STRATEGY_AND_BUSINESS("32","战略运营部"),
    STRATEGY_AND_PLANNING("33","战略规划部"),
    GENERAL_MANAGEMENT("34","综合管理部"),
    MARKETING_MANAGEMENT("35","市场营销部");



    /**
     * 缩写
     */
    private String code;
    private String value;

    ConstantEnum(String code,String value) {
        this.code = code;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取value
     * @param code
     * @return
     */
    public static String get(String code) {
        for (ConstantEnum val : ConstantEnum.values()) {
            if (val.getCode().equals(code)) {
                return val.getValue();
            }
        }
        return null;
    }

    /**
     * flag代表部门是否存在于这个枚举类中，具体逻辑是根据部门的编号是否存在确定的
     * @param code
     * @return
     */
    public static boolean getFlag(String code) {
        for (ConstantEnum val : ConstantEnum.values()) {
            if (val.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}