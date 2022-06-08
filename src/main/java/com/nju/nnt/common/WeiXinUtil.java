package com.nju.nnt.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class WeiXinUtil {


    @Value("${weixin.appid}")
    private static String appId;

    @Value("${weixin.appsecret}")
    private static String appSecret;

    public static JSONObject getSessionKeyOrOpenId(String code) {

        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=wx77e7389601f45c26&secret=a147f3e53be44dbde3fd3fda62ac8e21&js_code="+code+"&grant_type=authorization_code";
        String s = OkHttp.get(requestUrl);
        return JSONObject.parseObject(s);
    }


}
