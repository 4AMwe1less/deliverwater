package cn.deliver.water.util;

import cn.deliver.water.service.MyRedis;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxQrCodeUtil
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.12 上午 10:52
 * Version 1.0
 **/
@Component
@Slf4j
public class WxQrCodeUtil {

    @Resource
    MyRedis myRedis;

    /**
     *  以后所有的token获取都走这个方法
     * 用于获取access_token
     * @return  access_token
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        String result ="";
        if (StringUtils.isBlank(myRedis.get("access_token_key"))){
            String appID = "wxb0d149f1f9ad4e1b";
            String appSecret = "1411a43f4ced18646ed9526fd4d5b019";
            Map<String,Object> data = new HashMap<>();
            String accessToken="";
            try{
                result = HttpUtil.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                                +appID+"&secret="
                                +appSecret,
                        null);
            }catch (Exception e){
                e.printStackTrace();
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            accessToken = jsonObject.getString("access_token");
            myRedis.set("access_token_key",accessToken,1000);
            result = accessToken;
        }else{
            result = myRedis.get("access_token_key");
        }

        return result;
    }


//    public static String getminiqrQr(String accessToken, String uploadPath, HttpServletRequest request) {
//        String nowday = new SimpleDateFormat("yyyyMMddmmddss").format(new Date());
//        String ctxPath = uploadPath;
//        String fileName="twoCode"+nowday+".png";
//        String bizPath = "files";
//        String ppath =ctxPath + File.separator + bizPath + File.separator + nowday;
//        File file = new File(ctxPath + File.separator + bizPath + File.separator );
//        if (!file.exists()) {
//            file.mkdirs();// 创建文件根目录
//        }
//        String savePath = file.getPath() + File.separator + fileName;
//        String qrCode = bizPath + File.separator + nowday+ File.separator + fileName ;
////        if (ppath.contains("\\")) {
////            ppath = ppath.replace("\\", "/");
////        }
//        if (qrCode.contains("\\")) {
//            qrCode = qrCode.replace("\\", "/");
//        }
////        String codeUrl=ppath+"/twoCode.png";
//        System.out.print(qrCode);
//        System.out.print(savePath);
//        try
//        {
//            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken);
////            String wxCodeURL = WxCode_URL.replace("ACCESS_TOKEN",accessToken);
////            URL url = new URL(wxCodeURL);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");// 提交模式
//            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
//            // conn.setReadTimeout(2000);//读取超时 单位毫秒
//            // 发送POST请求必须设置如下两行
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setDoInput(true);
//            // 获取URLConnection对象对应的输出流
//            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
//            // 发送请求参数
//            JSONObject paramJson = new JSONObject();
//            paramJson.put("scene", "1234567890");
////            paramJson.put("page", "pages/index/index"); //小程序暂未发布
//            paramJson.put("width", 430);
//            paramJson.put("is_hyaline", true);
//            paramJson.put("auto_color", true);
//            System.out.println(paramJson);
//            printWriter.write(paramJson.toString());
//            // flush输出流的缓冲
//            printWriter.flush();
//            //开始获取数据
//            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
//            OutputStream os = new FileOutputStream(new File(savePath));
//            int len;
//            byte[] arr = new byte[1024];
//            while ((len = bis.read(arr)) != -1)
//            {
//                os.write(arr, 0, len);
//                os.flush();
//            }
//            os.close();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return qrCode;
//    }


//    public static void main(String[] args) throws Exception {
//    public void getQrCode() throws Exception {
//        String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=38_Q9nbhzr3ozPH3cDJxHwne2DZ7sSiGJTivxH6jygEc8vJDOMu_cC2Ye8Fk6PBOgYoTcoOmZKheBL65_gPYVjpcpauA2EGnRK-RoLWyQCRok0DoH7uD6lJ9NN6_Q8r-3_rjo7eej8WOabgfla7NHZbAFAZTL";
//        JSONObject data = new JSONObject();
//        data.put("scene", "1234567890");
//        data.put("path", "pages/index/index"); //小程序暂未发布
//        data.put("width", 430);
//        data.put("is_hyaline", true);
//        data.put("auto_color", true);
//        String filename = HttpClientUtil.sendPostRequestToPNG(url,data.toJSONString(),"D:\\");
//        //然後根據用戶id還有對應的filename進行對應保存就可以了
//        System.out.println(filename);
//    }
}


