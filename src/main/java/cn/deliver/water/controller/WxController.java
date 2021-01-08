package cn.deliver.water.controller;

import cn.deliver.water.entity.JmJson;
import cn.deliver.water.entity.OpenIdJson;
import cn.deliver.water.service.UserService;
import cn.deliver.water.util.HttpUtil;
import cn.deliver.water.util.WxCharUtil;
import cn.deliver.water.util.WxQrCodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxController
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.15 上午 11:51
 * Version 1.0
 **/
@RestController
public class WxController {
    @Resource
    UserService userService;

    private String appID = "wxb0d149f1f9ad4e1b";

    private String appSecret = "1411a43f4ced18646ed9526fd4d5b019";

//    @PostMapping("user/login")
//    public Map<String,Object> userLogin(@RequestParam("code") String code) throws IOException{
//        Map<String,Object> data = new HashMap<>();
//        String result = "";
//        try{
//            result = HttpUtil.doGet(
//                "https://api.weixin.qq.com/sns/jscode2session?appid="
//                        +this.appID+"&secret="
//                        +this.appSecret+"&js_code="
//                        +code
//                        +"&grant_type=authorization_code",null);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        OpenIdJson openIdJson = mapper.readValue(result,OpenIdJson.class);
//        System.out.println(result.toString());
//        System.out.println(openIdJson.getOpenid());
//        data.put("code",200);
//        data.put("data",result);
//        data.put("message","获取成功！");
//        return data;
//    }
//    @RequestMapping("user/getAccToken")
//    public Map<String,Object> getAccToken(){
//        Map<String,Object> data = new HashMap<>();
//        String result ="";
//        try{
//            result = HttpUtil.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
//                    +this.appID+"&secret="
//                    +this.appSecret,
//                    null);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        System.out.println(jsonObject.getString("access_token"));
//        data.put("code",200);
//        data.put("data",result);
//        data.put("message","获取成功！");
//        return data;
//    }

    @PostMapping("/user/getUserInfo")
    public Map<String,Object> getUserInfo(@RequestBody JmJson jmJson) {
        String encryptedData = jmJson.getEncryptedData();
        String iv = jmJson.getIv();
        String sessionKey = jmJson.getSessionKey();
        String result = WxCharUtil.decrypt(encryptedData, sessionKey, iv);
        String openid = jmJson.getOpenid();
        System.out.println(encryptedData);
        System.out.println(iv);
        System.out.println(sessionKey);
        Map<String, Object> data = new HashMap<>();
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(jsonObject.get("phoneNumber"));
        String phone = jsonObject.getString("phoneNumber");
        int i = userService.countOpenidNull(openid);//看看openid是否在用户表里
        if (i < 1) {//如果不在
            userService.insertOpenidAndPhone(phone, openid);//插入id和手机号
            userService.insertMoney(openid);//初始化金额表
            data.put("code", 200);
            data.put("data", result);
            data.put("message", "获取成功！");
            return data;
        }else{//如果在
            userService.updateUphoneByopenid(openid,phone);//更新一下手机号
            data.put("code",200);
            data.put("data",result);
            data.put("message", "登陆成功！");
            return data;
        }
    }
}

