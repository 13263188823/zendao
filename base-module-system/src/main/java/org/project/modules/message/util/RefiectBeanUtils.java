package org.project.modules.message.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class RefiectBeanUtils
{
    public static Object map2JavaBean(Class<?> clazz, Map<String, Object> map) throws Exception {
    Object javabean = clazz.newInstance(); // 构建对象
    Method[] methods = clazz.getMethods(); // 获取所有方法
    try {
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {//通过以set开头的方法判断找到每一个属性
                String field = method.getName(); // 截取属性名
                //System.out.println(field +"---------------->");
                field = field.substring(field.indexOf("set") + 3);
                //因为set后的属性名要大写，所以把第一位小写然后把之后的内容拼接过来。
                field = field.toLowerCase().charAt(0) + field.substring(1);
                if (map.containsKey(field)) {
                    //field就是set方法后的属性值
                    if("id".equals(field)){
                        String id =map.get(field)+"";
                        method.invoke(javabean,id);
                    }else{
                        //map.get代表的是返回键值
                        method.invoke(javabean, map.get(field));
                    }
                }
            }
        }
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    } catch (InvocationTargetException e) {
        e.printStackTrace();
    }
    return javabean;
}

    /**
     * 特殊字符处理
     * @param clazz
     * @param map
     * @return
     * @throws Exception
     */
    public static Object map2JavaBeanIncludeKeywords (Class<?> clazz, Map<String, Object> map) throws Exception {
        Object javabean = clazz.newInstance(); // 构建对象
        Method[] methods = clazz.getMethods(); // 获取所有方法
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    String field = method.getName(); // 截取属性名
                    //System.out.println(field +"---------------->");
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    //关键字处理,之后优化为枚举类,工时原因暂时处理
                    //只处理newInfo，并命名为new
                    if("newInfo".equals(field))
                        field ="new";

                    if (map.containsKey(field)) {
                        method.invoke(javabean, map.get(field));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return javabean;
    }

    /**
     * obj-obj
     * @param clazz
     * @param obj
     * @return
     * @throws Exception
     */
    public static Object Bean2JavaBean(Class<?> clazz, Object obj) throws Exception {
        Object javabean = clazz.newInstance(); // 构建对象
        Method[] methods = clazz.getMethods(); // 获取所有方法
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    String field = method.getName(); // 截取属性名
                    //System.out.println(field +"---------------->");
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Method[] desMethods = obj.getClass().getMethods();
                    for(Method desMethod :desMethods){
                        if(desMethod.getName().startsWith("set")){
                            String desField = desMethod.getName(); // 截取属性名
                            desField = desField.substring(desField.indexOf("set") + 3);
                            desField = desField.toLowerCase().charAt(0) + desField.substring(1);
                            //System.out.println(field +"---------------->");
                            if(desField.equalsIgnoreCase(field)){
                                method.invoke(javabean, field);
                            }
                        }
                    }
                    //if (map.containsKey(field)) {
                    //    method.invoke(javabean, map.get(field));
                    //}
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return javabean;
    }


}
