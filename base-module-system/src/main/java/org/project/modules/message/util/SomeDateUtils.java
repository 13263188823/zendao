package org.project.modules.message.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理类
 */
public class SomeDateUtils {
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getTodaytoYYYYMMDD() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }


    public static String getYesterday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        return dateFormat.format(calendar.getTime());
    }
    public static Date getYesterdayDate(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        return calendar.getTime();
    }

    public static String getThreeDaysAgo (){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-48);
        return dateFormat.format(calendar.getTime());
    }

    public static String getFourDaysAgo (){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-72);
        return dateFormat.format(calendar.getTime());
    }


    /**
     * 日期动态配置方法
     * @param day
     * @return
     */
    public static Map<String,String> dateProess(String day){
        Map<String,String> map = new HashMap<String,String>();
        //1.传入day进行判断
        if(day.equals("1")){
            map.put("before",SomeDateUtils.getFourDaysAgo());
            map.put("after",SomeDateUtils.getThreeDaysAgo());
            return map;
        }
        return null;
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

    //日期判断逻辑
    public static int getWeekStr(Date date) {
        Calendar cal = Calendar.getInstance();
        if(date!=null)
            cal.setTime(date);
        int week  = cal.get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
        }
        return 0;
    }

    public static void main(String[] args) {
        int a = 84464;
        int b = 84836;
        boolean c =SomeDateUtils.addIntcode(a, b);
        System.out.println(c);
    }
}