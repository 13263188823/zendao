package org.project.modules.zentao.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JDBCUtils
{

    /**
     *
     * @param url   数据库映射后链接
     * @param user  用户名
     * @param password  密码
     * @throws Exception
     */
    public static List<Map<String, Object>> runPastPortConcurrent(String url, String user, String password, String sql) throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 设置SSH本地端口转发后，访问本地ip+port就可以访问到远程的ip+port
            conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            Map<String, Object> dataMap = null;
            while (rs.next()){
                //暂时处理,解决方法如下
                ResultSetMetaData rsMeta=rs.getMetaData();
                int columnCount=rsMeta.getColumnCount();
                dataMap=new HashMap<String, Object>();
                for (int i=1; i<=columnCount; i++) {

                    dataMap.put(rsMeta.getColumnLabel(i), rs.getObject(i));
                }
                listMap.add(dataMap);
            }
            System.out.println(listMap.toString());

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs!=null) {
                rs.close();
                rs=null;
            }
            if (st!=null) {
                st.close();
                st=null;
            }
            if (conn!=null) {
                conn.close();
                conn=null;
            }
        }
        return listMap;
    }

}
