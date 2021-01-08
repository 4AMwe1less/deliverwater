package cn.deliver.water.wxpay.controller;


import cn.deliver.water.service.OrdersService;
import cn.deliver.water.service.UserMoneyService;
import cn.deliver.water.wxpay.sdk.MyWxPayConfig;
import cn.deliver.water.wxpay.sdk.WXPay;
import cn.deliver.water.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RefundController {

    private static Logger logger = LoggerFactory.getLogger(RefundController.class);


    @Resource
    private OrdersService orderService;

    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 订单退款
     *
     * @param out_trade_no 订单号
     * @param total_fee    订单金额 单位分 1元=100
     * @param refund_fee   退款金额 单位分 1元=100
     * @param refund_desc  退款原因
     * @return
     */
    @PostMapping("/Refund")
    public Map<String, Object> tx(String out_trade_no, String total_fee, String refund_fee, String refund_desc) throws Exception {
        Map<String, Object> rrmap = new HashMap<String, Object>();
        logger.info("订单：" + out_trade_no + "   开始退款。。。。。。");
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> resp = null;
        MyWxPayConfig myWxPayConfig = null;
        WXPay wxPay = null;
        try {
            myWxPayConfig = new MyWxPayConfig();
            wxPay = new WXPay(myWxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (refund_desc.equals("") || refund_desc == "" || refund_desc == null) {
            refund_desc = "用户未注明退款原因";
        }
        map.put("appid", myWxPayConfig.getAppID());
        map.put("mch_id", myWxPayConfig.getMchID());
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("out_trade_no", out_trade_no);
        map.put("out_refund_no", String.valueOf(WXPayUtil.next()));
        map.put("total_fee", total_fee);
        map.put("refund_fee", refund_fee);
        map.put("refund_desc", refund_desc);
        map.put("sign", WXPayUtil.generateSignature(map, myWxPayConfig.getKey()));
        logger.info("订单退款发送的数据：" + map.toString());
        try {
            resp = wxPay.refund(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("退款接口返回：" + resp);
        if (resp.get("return_code").equals("SUCCESS") && resp.get("result_code").equals("SUCCESS")) {
            logger.info("修改订单状态" + out_trade_no);
            String order_id = resp.get("out_trade_no");
            //回退扣除使用优惠券的返利金额
            logger.info("开始回退扣除使用优惠券的返利金额");
            Map<String, String> yhjMap = new HashMap<String, String>();
            yhjMap = userMoneyService.byOrderidGetIscoupon(order_id);
            if (yhjMap.get("iscoupon").equals("0")) {
                logger.info("使用了返利金额优惠");
                double discount_money = Double.parseDouble(yhjMap.get("discount_money")) * 100;
                userMoneyService.updateMoney(userMoneyService.byOrderidGetUserid(out_trade_no), String.valueOf(discount_money));
            } else {
                logger.info("未使用返利金额优惠");
            }
            logger.info("回退扣除使用优惠券的返利金额结束");
            int update_result = orderService.updateorder_state(order_id, "4", "", "", "");
            if (update_result >= 1) {
                logger.info("订单：" + out_trade_no + "退款结束。。。。。。");
                rrmap.put("code", 200);
                rrmap.put("data", "退款成功");
                rrmap.put("message", "退款成功");
                return rrmap;
            }

        }
        logger.info("订单：" + out_trade_no + "退款结束。。。。。。失败原因：" + resp.get("err_code_des"));
        rrmap.put("code", 500);
        rrmap.put("data", "退款失败");
        rrmap.put("message", "退款失败,原因：" + resp.get("err_code_des"));
        return rrmap;
    }
}
