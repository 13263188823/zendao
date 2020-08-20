package org.project.modules.zentao.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理类
 */
public class SomeDateUtils {
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    // 无意义订单处理时加的
    public static boolean addIntcode(int a, int b) {
        boolean flag = true;
        String str ="";
        while(flag){
            a++;
            str = "VO120071699997"+a;
            System.out.println(str+",");

            if (a == b) {
                return false;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        int a = 84464;
        int b = 84836;
        boolean c = SomeDateUtils.addIntcode(a, b);
        System.out.println(c);
    }
}