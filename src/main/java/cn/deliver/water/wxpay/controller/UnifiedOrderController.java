package cn.deliver.water.wxpay.controller;


import cn.deliver.water.service.OrdersService;
import cn.deliver.water.service.UserMoneyService;

import cn.deliver.water.wxpay.entity.UnifiedorderEntity;
import cn.deliver.water.wxpay.sdk.MyWxPayConfig;
import cn.deliver.water.wxpay.sdk.WXPay;
import cn.deliver.water.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RestController
public class UnifiedOrderController {

    private static Logger logger = LoggerFactory.getLogger(UnifiedOrderController.class);

    @Resource
    private OrdersService orderService;

    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 统一支付下单接口
     *
     * @param unifiedorderEntity commodity_msg 商品描述
     *                           total_fee 商品价格（单位分；例：1元=100）
     *                           openid openID
     *                           order_id 订单ID
     * @return 拉起支付所需参数
     * @throws Exception
     */
    @PostMapping("/unifiedOrder")
    public Map<String, String> unifiedOrder(UnifiedorderEntity unifiedorderEntity) throws Exception {
        MyWxPayConfig myWxPayConfig = new MyWxPayConfig();
        WXPay wxPay = new WXPay(myWxPayConfig);
        Map<String, String> data = new HashMap<String, String>();
        Map<String, String> resdataerror = new HashMap<String, String>();
        Map<String, String> resdata = new HashMap<String, String>();
        data.put("appid", myWxPayConfig.getAppID());
        data.put("mch_id", myWxPayConfig.getMchID());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", unifiedorderEntity.getCommodity_msg());
        data.put("out_trade_no", unifiedorderEntity.getOrder_id());
        data.put("fee_type", "CNY");
        data.put("total_fee", String.valueOf(unifiedorderEntity.getTotal_fee()));
        data.put("spbill_create_ip", "172.31.64.191");
        data.put("notify_url", myWxPayConfig.payCallback());
        data.put("trade_type", "JSAPI");
        data.put("openid", unifiedorderEntity.getOpenid());
        data.put("sign", WXPayUtil.generateSignature(data, myWxPayConfig.getKey()));
        logger.info("发送的数据" + WXPayUtil.mapToXml(data));
        Map<String, String> responseMap = null;
        try {
            responseMap = wxPay.unifiedOrder(data);
            logger.info("统一下单接口返回：" + responseMap);
            String return_code = (String) responseMap.get("return_code");
            String result_code = (String) responseMap.get("result_code");
            String return_msg = (String) responseMap.get("return_msg");
            Long timeStamp = System.currentTimeMillis() / 1000;
            if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
                String nonceStr = WXPayUtil.generateNonceStr();
                resdata.put("appId", myWxPayConfig.getAppID());
                resdata.put("timeStamp", timeStamp + "");
                resdata.put("nonceStr", nonceStr);
                resdata.put("package", "prepay_id=" + responseMap.get("prepay_id"));
                resdata.put("signType", "MD5");
                String sign = WXPayUtil.generateSignature(resdata, myWxPayConfig.getKey());
                resdata.put("paySign", sign);
            }
            logger.info(responseMap.toString());
            logger.info(WXPayUtil.mapToXml(responseMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resdata;
    }


    /**
     * 微信支付回调接口
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/payCallback")
    public String payCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("进入微信支付回调接口......");
        String responseXml = null;
        MyWxPayConfig myWxPayConfig = new MyWxPayConfig();
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream is = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            responseXml = sb.toString();
            logger.info("微信支付回调通知请求包: {}", responseXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (WXPayUtil.isSignatureValid(WXPayUtil.xmlToMap(responseXml), myWxPayConfig.getKey())) {
            try {
                Map<String, String> rmap = WXPayUtil.xmlToMap(responseXml);
                String result_code = rmap.get("result_code");
                String return_code = rmap.get("return_code");
                if ("SUCCESS".equals(result_code) && "SUCCESS".equals(return_code)) {
                    //扣除使用优惠券的返利金额
                    logger.info("开始扣除使用优惠券的返利金额");
                    Map<String, String> yhjMap = new HashMap<String, String>();
                    yhjMap = userMoneyService.byOrderidGetIscoupon(rmap.get("out_trade_no"));
                    //System.out.println(map.get("iscoupon"));
                    if (yhjMap.get("iscoupon").equals("0")) {
                        logger.info("使用了返利金额优惠");
                        double discount_money = Double.parseDouble(yhjMap.get("discount_money"));
                        discount_money = (discount_money - discount_money * 2) * 100;
                        userMoneyService.updateMoney(rmap.get("openid"), String.valueOf(discount_money));
                    } else {
                        logger.info("未使用返利金额优惠");
                    }
                    logger.info("扣除使用优惠券的返利金额结束");
                    int update_result = orderService.updateorder_state(rmap.get("out_trade_no"), "1", "", "", "");
                    if (update_result >= 1) {
                        map.put("return_code", "SUCCESS");
                        map.put("return_msg", "ok");
                    } else {
                        map.put("return_code", "FAIL");
                        map.put("return_msg", "订单状态修改失败");
                    }
                    return WXPayUtil.mapToXml(map);
                } else {
                    String err_code = rmap.get("err_code");
                    String err_code_des = rmap.get("err_code_des");
                    logger.info("订单：" + rmap.get("out_trade_no") + "支付失败！错误代码" + err_code + ";错误原因：" + err_code_des);
                    map.put("return_code", "FAIL");
                    map.put("return_msg", err_code_des);
                    return WXPayUtil.mapToXml(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("return_code", "FAIL");
        map.put("return_msg", "验签失败");
        return WXPayUtil.mapToXml(map);
    }


}