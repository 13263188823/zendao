package org.project.modules.message.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

/**
 * @author yanghao
 * @version DingTalkTest.java, v 0.1 2019-03-29 11:36
 */
public class DingTalkUtil {


    private static String result;
    /**
     * 组装请求报文
     * @param content
     * @return
     */
    private static String buildReqStr(String content, boolean isAtAll, List<String> mobileList) {
        //消息内容
        Map<String, String> contentMap = Maps.newHashMap();
        contentMap.put("content", content);

        //通知人
        Map<String, Object> atMap = Maps.newHashMap();
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", mobileList);

        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);

        return JSON.toJSONString(reqMap);
    }

    /**
     * 处理
     * @param dingUrl  路径
     * @param sendMsg  消息
     * @return
     */
    public static String process(String dingUrl,String sendMsg){
        try {
            //钉钉机器人地址（webhook）
            //dingUrl = "https://oapi.dingtalk.com/robot/send?access_token=8f03162faa65c17d67fd6c3e7298f04b197e310dd3342b428417ff356dbf9c6c";

            //是否通知所有人
            boolean isAtAll = false;
            //通知具体人的手机号码列表
            List<String> mobileList = Lists.newArrayList();

            //钉钉机器人消息内容
            String content = "推送:" +sendMsg;
            //String content = "钉钉机器人！" +sendMsg;
            //组装请求内容
            String reqStr = buildReqStr(content, isAtAll, mobileList);

            //推送消息（http请求）
            result= HttpUtils.sendInfo(dingUrl, reqStr);
            System.out.println("result == " + result);

        }catch (Exception e){
            e.printStackTrace();
            result = "ERROR_"+e.getMessage();
        }
        return result;
    }

    public static void main(String[] args){

    }
}
