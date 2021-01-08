package cn.deliver.water.controller;


import cn.deliver.water.service.MyRedis;
import cn.deliver.water.service.UserService;
import cn.deliver.water.util.HttpClientUtil;
import cn.deliver.water.util.WxQrCodeUtil;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName QrCodeController
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.10 上午 9:56
 * Version 1.0
 **/
@RestController
public class QrCodeController {

    @Resource
    MyRedis myRedis;
    @Resource
    UserService userService;
    @Resource
    WxQrCodeUtil wxQrCodeUtil;
    private static Logger LOG = LoggerFactory.getLogger(QrCodeController.class);

    /**
     * 生成带参小程序二维码
     */
    @PostMapping("/user/getQrCode")
    public Map<String, Object> postMiniqrQr(String openid) throws Exception {
        System.out.println(openid);
        //查看用户有没有下过单（已完成）
        int j = userService.countOrderMo(openid);
        Map<String, Object> data = new HashMap<>();
        String acc1 = wxQrCodeUtil.getAccessToken();
        if (j > 0) {//如果下过单，可以生成二维码
            System.out.println(acc1);//去redis里拿sessionkey
                String aa = userService.getQrCode(openid);//根据id查二维码路径
                if (!StringUtils.isEmpty(aa)) { //如果根据id查二维码路径不是空的，直接获取路径
                    data.put("code", 200);
                    data.put("data", aa);
                    data.put("message", "二维码获取成功！");
                    return data;
                } else {//如果是空的，生成二维码
                    String accessToken = acc1;
                    System.out.println(accessToken);
                    String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + accessToken;
                    JSONObject params = new JSONObject();
                    params.put("scene", "id:" + openid);
                    params.put("path", "pages/home/home?openid=" + openid); //小程序暂未发布
                    params.put("width", 430);
                    params.put("is_hyaline", true);
                    params.put("auto_color", true);
//                System.out.println("/home/package/deliverWater/upload/");
                    String filename = HttpClientUtil.sendPostRequestToJPG(url, params.toJSONString(), "/home/package/Yuelao/upload/");
                    //然後根據用戶id還有對應的filename進行對應保存就可以了
                    String realUrl = "https://www.jinghexinxi.top/other/upload/" +filename;
                            userService.setQrCode(openid, realUrl);
                    System.out.println(filename);
                    System.out.println(realUrl);
                    data.put("code", 200);
                    data.put("data", realUrl);
                    data.put("message", "生成成功！");
                    return data;
                }
        } else {//用户没有下单过，不能生成
            data.put("code", 280);
            data.put("message", "对不起，您还没有下单过，暂时无法生成二维码!");
            return data;

        }
    }
}