package cn.deliver.water.util;

/**
 * @ClassName HttpClientUtils
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.13 上午 10:52
 * Version 1.0
 **/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

/**
 * Http请求工具类
 * gaoxiang
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();


    /**
     * Get请求
     *
     * @param reqURL 请求地址
     * @param params 请求参数(map)
     * @param auth   Authorization
     * @return 字符串
     */
    public static String getRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset, String auth) {
        CloseableHttpClient httpClient = httpClientBuilder.build();
        String responseContent = null;
        // 封装请求数据
        if (params != null && params.size() > 0) {
            reqURL = reqURL + "?";
        }
        if(params != null){
            int size = params.size();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                reqURL = reqURL + entry.getKey() + "=" + entry.getValue();
                if (size != 1) {
                    reqURL = reqURL + "&";
                    size--;
                }
            }
        }
        // 初始化Get
        HttpGet httpGet = new HttpGet(reqURL);
        try {
            // 头信息
            httpGet.addHeader("Content-Type", "application/json");
            if (auth != null) {
                httpGet.addHeader("Authorization", auth);
            }
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            return null;
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * Get请求
     *
     * @param reqURL        请求地址
     * @param params        请求参数(map)
     * @param encodeCharset 编码集（允许为空）
     * @param decodeCharset 解码集（允许为空）
     * @return 字符串
     */
    public static String getRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset) {
        CloseableHttpClient httpClient = httpClientBuilder.build();
        String responseContent = null;
        // 封装请求数据
        if (params != null && params.size() > 0) {
            reqURL = reqURL + "?";
        }
        int size = params.size();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            reqURL = reqURL + entry.getKey() + "=" + entry.getValue();
            if (size != 1) {
                reqURL = reqURL + "&";
                size--;
            }
        }
        logger.info("请求的url:{}",reqURL);
        // 初始化Get
        HttpGet httpGet = new HttpGet(reqURL);
        try {
            // 头信息
            httpGet.addHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            return null;
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * delete请求
     *
     * @param reqURL        请求地址
     * @param params        请求参数(map)
     * @param encodeCharset 编码集（允许为空）
     * @param decodeCharset 解码集（允许为空）
     * @param auth          Authorization
     * @return 字符串
     */
    public static String deleteRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset, String auth) {
        CloseableHttpClient httpClient = httpClientBuilder.build();
        String responseContent = null;
        // 封装请求数据
        if (params != null && params.size() > 0) {
            reqURL = reqURL + "?";
        }
        int size = params.size();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            reqURL = reqURL + entry.getKey() + "=" + entry.getValue();
            if (size != 1) {
                reqURL = reqURL + "&";
                size--;
            }
        }
        // 初始化Delete
        HttpDelete httpdelete = new HttpDelete(reqURL);
        try {
            // 头信息
            httpdelete.addHeader("Content-Type", "application/json");
            if (auth != null) {
                httpdelete.addHeader("Authorization", auth);
            }
            HttpResponse response = httpClient.execute(httpdelete);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            return null;
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }


    /**
     * delete请求
     *
     * @param reqURL        请求地址
     * @param params        请求参数(map)
     * @param encodeCharset 编码集（允许为空）
     * @param decodeCharset 解码集（允许为空）
     * @param auth          Authorization
     * @return 字符串
     */
    public static String deleteUserRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset, String auth) {
        CloseableHttpClient httpClient = httpClientBuilder.build();
        String responseContent = null;
        // 封装请求数据
        if (params != null && params.size() > 0) {
            reqURL = reqURL + "?";
        }
        int size = params.size();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            reqURL = reqURL + entry.getKey() + "=" + entry.getValue();
            if (size != 1) {
                reqURL = reqURL + "&";
                size--;
            }
        }
        // 初始化Delete
        HttpDelete httpdelete = new HttpDelete(reqURL);
        try {
            // 头信息
            httpdelete.addHeader("Content-Type", "application/json");
            if (auth != null) {
                httpdelete.addHeader("Authorization", auth);
            }
            HttpResponse response = httpClient.execute(httpdelete);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            return null;
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * Post请求
     *
     * @param reqURL        请求地址
     * @param paramStr      请求参数（Json）
     * @param encodeCharset 编码集
     * @param decodeCharset 解码集
     * @param auth          Authorization
     * @return 字符串
     */
    public static String postRequest(String reqURL, String paramStr, String encodeCharset, String decodeCharset, String auth) {
        CloseableHttpClient httpClient = httpClientBuilder.build();
        String responseContent = null;
        HttpPost httpPost = new HttpPost(reqURL);
        try {
            StringEntity urlEntity = new StringEntity(paramStr, Charset.forName("UTF-8"));
            httpPost.setEntity(urlEntity);

            httpPost.addHeader("Content-Type", "application/json");
            if (auth != null) {
                httpPost.addHeader("Authorization", auth);
            }
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            return null;
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * Put请求
     *
     * @param reqURL        请求地址
     * @param paramStr      请求参数（Json）
     * @param encodeCharset 编码集
     * @param decodeCharset 解码集
     * @param auth          Authorization
     * @return 字符串
     */
    public static String putRequest(String reqURL, String paramStr, String encodeCharset, String decodeCharset, String auth) {
        CloseableHttpClient httpClient = httpClientBuilder.build();
        String responseContent = null;
        HttpPut httpPut = new HttpPut(reqURL);
        try {
            StringEntity urlEntity = new StringEntity(paramStr, Charset.forName("UTF-8"));
            httpPut.setEntity(urlEntity);

            httpPut.addHeader("Content-Type", "application/json");
            if (auth != null) {
                httpPut.addHeader("Authorization", auth);
            }
            HttpResponse response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            return null;
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     *
     * @ClassName: HttpClientUtil
     * @Description: post url参数
     * @author liuzh
     * @date 2019/6/6 16:22
     *
     */
    public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset, String auth){
        CloseableHttpClient httpClient = httpClientBuilder.build();
        String responseContent = null;
        if (params != null && params.size() > 0) {
            reqURL = reqURL + "?";
        }
        int size = params.size();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            reqURL = reqURL + entry.getKey() + "=" + entry.getValue();
            if (size != 1) {
                reqURL = reqURL + "&";
                size--;
            }
        }
        HttpPost httpPost = new HttpPost(reqURL);
        try {
            httpPost.addHeader("Content-Type", "application/json");
            if (auth != null) {
                httpPost.addHeader("Authorization", auth);
            }
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            return null;
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * @Title: sendPostRequest
     * @Description: 微信用http请求
     * @author sunxiao,liuzhen
     * @param url 请求地址
     * @param param 请求参数[格式:key1=value1&key2=value2 || JSON.toString()]
     * @return java.lang.String
     */
    public static String sendPostRequest(String url, String param) throws Exception {
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            // 创建URL对象
            URL realUrl = new URL(url);
            // 打开URL的链接
            URLConnection conn = realUrl.openConnection();
            // 设置Head信息
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // POST请求设置
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 获取输入流,读取响应请求
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            // 临时参数
            String line;
            // 获取响应结果
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch(Exception e) {
            throw new Exception("请求失败");
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                throw new Exception("请求失败");
            }
        }
        return result;
    }


    /**
     * @Title: sendPostRequest
     * @Description: 微信用http请求
     * @author sunxiao,liuzhen
     * @param url 请求地址
     * @param param 请求参数[格式:key1=value1&key2=value2 || JSON.toString()]
     * @return java.lang.String
     */
    public static String sendPostRequestToJPG(String url, String param,String path) throws Exception {
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            // 创建URL对象
            URL realUrl = new URL(url);
            // 打开URL的链接
            URLConnection conn = realUrl.openConnection();
            // 设置Head信息
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // POST请求设置
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 获取输入流,读取响应请求
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            String fileName = UUID.randomUUID()+".JPG";
            OutputStream os = new FileOutputStream(new File(path+fileName));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1)
            {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            result = fileName;
        }catch(Exception e) {
            throw new Exception("请求失败");
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                throw new Exception("请求失败");
            }
        }
        return result;
    }
}
