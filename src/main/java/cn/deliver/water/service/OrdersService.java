package cn.deliver.water.service;

import cn.deliver.water.entity.Order;
import cn.deliver.water.entity.OrderInfo;
import cn.deliver.water.entity.OrderMxInfo;

import java.util.List;
import java.util.Map;

public interface OrdersService {
    List<OrderInfo> listorder(OrderInfo orderInfo);//查询订单

    int addOrder(OrderInfo orderInfo);//添加订单

    int updateorder_state(String order_id, String order_state, String order_over_time,String psr,String psr_phone);//修改订单状态

    String  turnoverAndOrderNum(String order_qs_time, String order_js_time);

    Map<String,Integer> turnoverAndOrderNumToday();

    String  turnoverAndOrderNumWeek();

    List<Order> getOrderWeek();

    List<Order> getOrderTime(String order_qs_time, String order_js_time);

    int addOrderMx(OrderMxInfo orderMxInfo);

    String selectTurnOverTime(String order_qs_time, String order_js_time);

    String selectTurnOverWeek();

    OrderInfo bycdkgetorder(String cdk);

    int bycdkaddsjrmsg(String cdk, String delivery_address, String sjr_name, String sjr_phone,String  userid);

    int selectCountOrder(String cdk);

    String getMoneyByID(String order_id);
}
