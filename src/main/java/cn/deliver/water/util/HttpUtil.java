package cn.deliver.water.util;



import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName HttpUtil
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.15 下午 12:02
 * Version 1.0
 **/
public class HttpUtil {
    public static String doGet(String urlPath, HashMap<String, Object> params) throws  Exception{
        StringBuilder sb = new StringBuilder(urlPath);
        if (params != null && !params.isEmpty()){
            //说明有参数
            Set<Map.Entry<String,Object>> set = params.entrySet();
            for (Map.Entry<String,Object> entry : set){
                String key = entry.getKey();
                String value = "";
                if (null != entry.getValue()){
                    value = entry.getValue().toString();
                    //转码
                    value = URLEncoder.encode(value,"UTF-8");
                }
                sb.append(key).append("=").append(value).append("&");
            }
            sb.deleteCharAt(sb.length()-1);//删除最后一个&
        }
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);//5s超时
        conn.setRequestMethod("GET");
        if (conn.getResponseCode()== HttpStatus.SC_OK){//200
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sbs = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                sbs.append(line);
            }
            return sbs.toString();
        }
        return null;
    }
}
