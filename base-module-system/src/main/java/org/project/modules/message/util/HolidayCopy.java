package org.project.modules.message.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
/**
 * @version 1.0
 * @ClassName HolidayCopy
 * @Description
 * @Author 74981
 * @Date 2018/9/27 14:30
 */
public class HolidayCopy {
    /**
     * @Description: 入参格式为“yyyyMMdd”如“20181001”
     * @return result(0 工作日 1周末 2节假日)
     */
    public static String request(String httpArg) {
        //工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
        String httpUrl = "http://tool.bitefu.net/jiari/";
        String result = "";
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?d=" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                //sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: JSONString转Map
     * @param
     * @return
     * @throws
     * @author liushiwei
     * @date 2018/9/27 15:42
     */
    private static Map<String, Object> jsonStringToMap(String json) {
        //String转成JSONObject形式
        JSONObject jsonArray = JSONObject.parseObject(json);
        Map<String, Object> param = JSONObject.parseObject(jsonArray.toJSONString(), new TypeReference<Map<String, Object>>() {});
        return param;
    }

    public static String HolidayGet(String yyyyMMdd) {
        // https://tool.bitefu.net/jiari/?d=20191014
        //LocalDate localDate = LocalDate.now();
        //LocalDate localDate = LocalDate.of(2018, 12, 31);
        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //localDate = localDate.plusDays(1);
        //String yyyyMMdd = localDate.format(dateTimeFormatter);
        String url = "https://tool.bitefu.net/jiari/?d=" + yyyyMMdd;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> res = restTemplate.getForEntity(url, Integer.class);
        return res.getBody().toString();
    }

    public static void main(String[] args) {
        System.out.println(HolidayCopy.HolidayGet("20200731"));
    }

}