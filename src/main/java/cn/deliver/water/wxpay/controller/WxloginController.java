package cn.deliver.water.wxpay.controller;

import cn.deliver.water.entity.JmJson;
import cn.deliver.water.wxpay.sdk.MyWxPayConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Pos;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;


@RestController
public class WxloginController {

    MyWxPayConfig myWxPayConfig = null;

    /**
     * @param code 获取openID所需的code
     * @return session_key和openID
     */
//    @PostMapping("/login/getOpenidAndSession_key")
    @PostMapping("user/login")
    public Map<String,Object> getOpenidAndSession_key(String code) throws Exception {
        Map<String,Object> data = new HashMap<>();
        myWxPayConfig = new MyWxPayConfig();
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + myWxPayConfig.getAppID() + "&secret=" + myWxPayConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        try {
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        String session_key = jsonObject.get("session_key") + "";
        String openid = jsonObject.get("openid") + "";
//        System.out.println("session_key==" + session_key);
//        System.out.println("openid==" + openid);
        data.put("code",200);
        data.put("data",resultString);
        data.put("message","获取成功！");
        return data;
    }



////    @PostMapping("/login/jmPhone")
//   @PostMapping("/user/getUserInfo")
//    public Map<String,Object> jmPhone(@RequestBody JmJson jmJson) {
////       JmJson jmJson = JSON.parseObject(JMjson,JmJson.class);
//       String sessionKey = jmJson.getSessionKey();
//       String encryptedData = jmJson.getEncryptedData();
//       String iv = jmJson.getIv();
//       System.out.println(sessionKey);
//       System.out.println(encryptedData);
//       System.out.println(iv);
//       Map<String,Object> data = new HashMap<>();
////        String sessionkey = "+qMc8ESt9QstC8m2GvtNug==";
////        String encryptedData = "s+eJBPbAPPQw2OkU8XW3ds4lVG1rdT1wDgJve2XwsyGqiUte45fSOuX8zNgCItvi1IGUN82QvfXq0jWMa9IKv7l+oNZRC1FQ0uD5Lenqa1Vl+VD24dOzNv255Kwie1MaUlLxTN0DWKhRNlVix5Whk5f6RAs+wQU56lvA5I//d1N8qvIZ+WBL9bG255emMdhPBM2AChSILFSiZXhe6Q7C26hG3UC98KSbFbCTBMQJsmaxlJByZwJ0xi5ZKqKq0cHVM5hrRXgJNhKwIIBG+X031UWxeg2y8VK84zQAwc1oDVaZ2/et25LkIydJHbIL3e60F8R2PHwfNNqa+ucbvSpPze7bFTdtG59yAB+Rz/2tGW5kI7By+h6tY/wVPs8GRqsgNZi0yzBmWwvxQ/yuOpbKJLgv511dO+9ee9XoKK2h8y/HTJoXgcqvU9xJ5PUMoL8FF2eb0SpFZmXBE6g/+9v1bQ==";
////        String ivStr = "I8AYLdInnBVBxLXc4fKIkQ==";
//        byte[] encData = Base64.decode(encryptedData);
//        byte[] ivStr = Base64.decode(iv);
//        byte[] key = Base64.decode(sessionKey);
//        System.out.println(encData.length);
//        try {
//            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivStr);
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
//            String opendata = new String(cipher.doFinal(encData), "UTF-8");
//            data.put("code",200);
//            data.put("data",opendata);
//            data.put("message","解密成功！");
//            return data;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            data.put("code",201);
//            data.put("message","解密失败！");
//            return data;
//        }
//    }
}
