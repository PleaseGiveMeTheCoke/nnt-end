package com.nju.nnt.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.nnt.common.CheckParams;
import com.nju.nnt.common.JWTUtil;
import com.nju.nnt.common.Response;
import com.nju.nnt.common.WeiXinUtil;
import com.nju.nnt.entity.Goods;
import com.nju.nnt.entity.User;
import com.nju.nnt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping("/wxlogin")
    public Response WxLogin(@RequestBody JSONObject data){
        log.info("检查参数");
        String lossParams = CheckParams.check(data,new String[]{
                "code","rawData","signature"
        });
        if (!"".equals(lossParams)){
            log.error("参数类型不匹配,缺少参数："+lossParams);
            return Response.error("参数类型不匹配,缺少参数："+lossParams);
        }
        String rawData = data.getString("rawData");
        String code = data.getString("code");
        String signature = data.getString("signature");
        JSONObject userInfo = data.getJSONObject("rawData");

        log.info("wxlogin返回的code为: {}",code);
        log.info("接收到的用户信息为: {}",userInfo);
        log.info("接收到的数据签名为: {}",signature);

        //利用code得到openid和sessionKey
        JSONObject sessionKeyOrOpenId = WeiXinUtil.getSessionKeyOrOpenId(code);

        log.info("利用code调用接口得到: {}",sessionKeyOrOpenId);
        String openId = sessionKeyOrOpenId.getString("openid");
        String sessionKey = sessionKeyOrOpenId.getString("session_key");
        //利用sessionKey鉴定数据的真实性
        String mySignature = DigestUtils.sha1Hex(userInfo + sessionKey);

        log.info("利用sessionKey算出的真实签名为: {}",mySignature);
        if(!mySignature.equals(signature)){
            //数据真实性异常
            //log.error("数据签名校验失败");
            //return Response.error(400,"数据签名校验失败",null);
        }
        //利用openId实现注册或者登录
        User user = userService.selectUserByOpenId(openId);
        if(user==null){
            //需要注册
            User newUser = new User();
            newUser.setOpenId(openId);
            newUser.setGender(userInfo.getString("gender"));
            newUser.setAvatarUrl(userInfo.getString("avatarUrl"));
            newUser.setNickname(userInfo.getString("nickname"));
            userService.registerUser(newUser);
            user = newUser;
        }
        String token = null;
        //TODO 注册或者登录之后生成token返回给客户端
        try {
            token = JWTUtil.generateToken(user, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("生成token:{}",token);

        JSONObject respData = new JSONObject();
        respData.put("token",token);
        respData.put("userId",openId );
        log.info("openId = {}",openId);
        return Response.success(respData,code);
    }

    @RequestMapping("/detail")
    public Response getUserDetail(@RequestParam Long userId){
        User userDetail = userService.getUserDetail(userId);
        log.info("UserDetail: {}",userDetail);
        return Response.success(userDetail);
    }
}
