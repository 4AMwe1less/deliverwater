package cn.deliver.water.controller;

import cn.deliver.water.entity.Order;
import cn.deliver.water.entity.OrderInfo;
import cn.deliver.water.entity.OrderMxInfo;
import cn.deliver.water.service.AdminService;
import cn.deliver.water.service.OrdersService;
import cn.deliver.water.service.UserMoneyService;
import cn.deliver.water.wxpay.sdk.WXPayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.18 上午 11:17
 * Version 1.0
 **/
@RestController
public class OrdersController {
    private static Logger logger = LoggerFactory.getLogger(OrdersController.class);
    @Resource
    private OrdersService ordersService;
    @Resource
    private AdminService adminService;
    @Resource
    private UserMoneyService userMoneyService;


    /**
     * 查询订单
     * 条件
     * order_state 订单状态
     * order_type 订单类型
     * ID 用户ID
     * psr 派送人
     * 以上参数均可为空，全为空则表示查询全部，条件查询自由搭配
     *
     * @param orderInfo
     * @return
     */
    @PostMapping("/order/lselect")
    public Map<String, Object> listorder(OrderInfo orderInfo) {
        System.out.println(orderInfo);
        Map<String, Object> data = new HashMap<>();
        List<OrderInfo> list = ordersService.listorder(orderInfo);
        data.put("code", 200);
        data.put("data", list);
        data.put("message", "查询成功！");
        return data;
    }

    /**
     * @param zbjson 总表数据
     * @param mxjson 明细表数据
     * @return 添加成功
     */
    @PostMapping("/order/add")
    public Map<String, Object> addOrder(String zbjson, String mxjson) {
        System.out.println(zbjson);
        System.out.println(mxjson);
        Map<String, Object> data = new HashMap<>();
        OrderInfo orderInfo = JSON.parseObject(zbjson, OrderInfo.class);
        logger.info("=================开始插入订单主表=======================");
        String orderid = String.valueOf(WXPayUtil.next());
        orderInfo.setID(orderid);
        System.out.println("ID====="+orderid);
        double money = Double.parseDouble(orderInfo.getDiscount_money()) - Double.parseDouble(orderInfo.getDiscount_money()) * 2;
        orderInfo.setDiscount_money(String.valueOf(money));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        orderInfo.setOrder_submit_time(formatter.format(date));
        if (orderInfo.getOrder_type().equals("1")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            uuid = uuid.substring(0, uuid.length() - 24);
            orderInfo.setCdk(uuid);
        }
        int add = 0;
        try {
            add = ordersService.addOrder(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (add > 0) {
            logger.info("=================插入订单主表结束，插入成功=======================");
        } else {
            logger.info("=================插入订单主表结束，插入失败=======================");
        }
        logger.info("=================开始插入订单明细表=======================");
        List<OrderMxInfo> orderMxInfos = JSONObject.parseArray(mxjson, OrderMxInfo.class);
        logger.info("共" + orderMxInfos.size() + "条明细");
        for (int i = 0; i < orderMxInfos.size(); i++) {
            orderMxInfos.get(i).setOrder_id(orderid);
            if (ordersService.addOrderMx(orderMxInfos.get(i)) >= 1) {
                logger.info("第" + (i + 1) + "条明细插入成功");
            } else {
                logger.info("第" + (i + 1) + "条明细插入失败");
            }
        }
        logger.info("=================插入订单明细表完成=======================");
        data.put("code", 200);
        data.put("orderid", orderid);
        data.put("message", "添加成功");
        return data;
    }


    /**
     * @param order_id    订单ID 必填
     * @param order_state 订单状态 必填
     * @param psr         派送人 非必填 订单状态为2的时候必填
     * @return
     * @Param psr_phone 派送人电话 非必填 订单状态为2的时候必填
     */
    @PostMapping("/order/update")
    public Map<String, Object> updateorder_state(String order_id, String order_state, @RequestParam(required = false) String psr, @RequestParam(required = false) String psr_phone) {
        Map<String, Object> data = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String order_over_time = formatter.format(date);
        int add = ordersService.updateorder_state(order_id, order_state, order_over_time, psr, psr_phone);
        if (add > 0) {
            if (order_state.equals("3")) {
                //给上家增加返利订单个数
                logger.info("获取此订单用户ID");
                String thisuserid = userMoneyService.byOrderidGetUserid(order_id);
                logger.info(" 获取上家用户ID");
                String SJuserid = userMoneyService.getUserFatherID(thisuserid);
                if (SJuserid != null) {
                    logger.info("修改上家的订单数量");
                    userMoneyService.updateOrderNumber(SJuserid);
                    logger.info("获取订单金额并计算返利金额");
                    double money = Integer.valueOf(userMoneyService.byOrderidGetOrderMoney(order_id)) * 0.3;
                    logger.info(money + "修改返利金额");
                    userMoneyService.updateMoney(SJuserid, String.valueOf(money));
                } else {
                    logger.info("此用户无上家，无需为上家增加积分");
                }
            }
            data.put("code", 200);
            data.put("message", "更新成功！");
            return data;
        } else {
            data.put("code", 201);
            data.put("message", "更新失败！");
            return data;
        }
    }

    /**
     * 查询营业额和订单数量
     *
     * @param order_qs_time 起始时间 取值示例：2020-09-18或者2020-09-18 08:00:00
     * @param order_js_time 结束时间 取值示例：同上
     *                      两个参数构成一个区间，参数非必填，不填则查询全部营业额与订单数量
     * @return Map<String, Integer> 示例：{order_numer=6, turnover=120.0}
     * order_numer订单数量   turnover 营业额
     */
    @PostMapping("/order/turnoverAndOrderNum")
    public Map<String, Object> turnoverAndOrderNum(@RequestParam(required = false) String order_qs_time, String order_js_time) {
        String order_money = ordersService.turnoverAndOrderNum(order_qs_time, order_js_time);
        List<Order> oList = ordersService.getOrderTime(order_qs_time, order_js_time);
        String order_number = ordersService.selectTurnOverTime(order_qs_time, order_js_time);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("message", "查询成功！");
        data.put("data", oList);
        data.put("order_number", order_number);
        data.put("order_money", order_money);
        return data;
    }

    @PostMapping("/order/getOrderToday")
    public Map<String, Object> turnoverAndOrderNumToday() {
        Map<String, Integer> map = ordersService.turnoverAndOrderNumToday();
        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("message", "查询成功！");
        data.put("data", map);
        return data;
    }

    @PostMapping("/order/getOrderWeek")
    public Map<String, Object> turnoverAndOrderNumWeek() {
        String order_money = ordersService.turnoverAndOrderNumWeek();
        String order_number = ordersService.selectTurnOverWeek();
        List<Order> oList = ordersService.getOrderWeek();
//        Map<String,Object> maps = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
            data.put("code", 200);
            data.put("message", "查询成功！");
            data.put("data", oList);
            data.put("order_number", order_number);
            data.put("order_money", order_money);
            return data;
    }

    /**
     * 根据兑换码查询订单或添加收货人信息
     * 根据cdk查询订单时，收件人信息不要传
     * 修改收件人信息时cdk与收件人信息全部传
     *
     * @param cdk              兑换码
     * @param sjr_name         收件人名字
     * @param delivery_address 收件人地址
     * @param sjr_phone        收件人电话
     * @return 查询时返回订单json对象，修改时返回修改成功或失败
     */
    @PostMapping("/order/bycdkselorder")
    public Map<String, Object> bycdkselorder(String cdk, @RequestParam(required = false) String delivery_address, @RequestParam(required = false) String sjr_name, @RequestParam(required = false) String sjr_phone, @RequestParam(required = false) String userid) {
        //if (!cdk.equals("") && delivery_address.equals("") && sjr_name.equals("") && sjr_phone.equals("")) {
        Map<String, Object> data = new HashMap<>();
        String re = "";
        OrderInfo orderInfo = null;
        if (!cdk.equals("") && delivery_address == null && sjr_name == null && sjr_phone == null) {
            logger.info("查询");
            int i = ordersService.selectCountOrder(cdk);
            if (i < 1) {
                data.put("code", 205);
                data.put("message", "根据该cdk暂未查询到相应的订单！");
                return data;
            } else {
                orderInfo = ordersService.bycdkgetorder(cdk);
                re = orderInfo.toString();
                data.put("code", 200);
                data.put("data", orderInfo);
                data.put("message", "查询成功！");
                return data;
            }
        } else if (!cdk.equals("") && !delivery_address.equals("") && !sjr_name.equals("") && !sjr_phone.equals("")) {
            logger.info("修改");
            int updateRe = ordersService.bycdkaddsjrmsg(cdk, delivery_address, sjr_name, sjr_phone, userid);
            if (updateRe >= 1) {
                data.put("code", 200);
                data.put("message", "修改成功！");
                return data;
            } else {
                data.put("code", 201);
                data.put("message", "修改失败！");
                return data;
            }
        }
        data.put("code", 203);
        data.put("message", "网络未知错误！");
        return data;
    }

}
