package cn.deliver.water.service.impl;

import cn.deliver.water.entity.Order;
import cn.deliver.water.entity.OrderInfo;
import cn.deliver.water.entity.OrderMxInfo;
import cn.deliver.water.mapper.OrdersMapper;
import cn.deliver.water.service.OrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrdersServiceImpl
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.18 上午 11:15
 * Version 1.0
 **/
@Service
public class OrdersServiceImpl implements OrdersService {
    @Resource
    private OrdersMapper ordersMapper;

    @Override
    public List<OrderInfo> listorder(OrderInfo orderInfo) {
        return ordersMapper.listorder(orderInfo);
    }

    @Override
    public int addOrder(OrderInfo orderInfo) {
        return ordersMapper.addOrder(orderInfo);
    }

    @Override
    public int updateorder_state(String order_id, String order_state, String order_over_time, String psr,String psr_phone) {
        return ordersMapper.updateorder_state(order_id, order_state, order_over_time, psr,psr_phone);
    }

    @Override
    public String turnoverAndOrderNum(String order_qs_time, String order_js_time) {
        return ordersMapper.turnoverAndOrderNum(order_qs_time, order_js_time);
    }

    @Override
    public Map<String,Integer> turnoverAndOrderNumToday() {
        return ordersMapper.turnoverAndOrderNumToday();
    }

    @Override
    public String turnoverAndOrderNumWeek() {
        return ordersMapper.turnoverAndOrderNumWeek();
    }

    @Override
    public List<Order>  getOrderWeek() {
        return ordersMapper.getOrderWeek();
    }

    @Override
    public List<Order> getOrderTime(String order_qs_time, String order_js_time) {
        return ordersMapper.getOrderTime(order_qs_time, order_js_time);
    }

    @Override
    public int addOrderMx(OrderMxInfo orderMxInfo) {
        return ordersMapper.addOrderMx(orderMxInfo);
    }

    @Override
    public String selectTurnOverTime(String order_qs_time, String order_js_time) {
        return ordersMapper.selectTurnOverTime(order_qs_time, order_js_time);
    }

    @Override
    public String selectTurnOverWeek() {
        return ordersMapper.selectTurnOverWeek();
    }

    @Override
    public OrderInfo bycdkgetorder(String cdk) {
        return ordersMapper.bycdkgetorder(cdk);
    }

    @Override
    public int bycdkaddsjrmsg(String cdk, String delivery_address, String sjr_name, String sjr_phone, String userid) {
        return ordersMapper.bycdkaddsjrmsg(cdk, delivery_address, sjr_name, sjr_phone, userid);
    }


    @Override
    public int selectCountOrder(String cdk) {
        return ordersMapper.selectCountOrder(cdk);
    }

    @Override
    public String getMoneyByID(String order_id) {
        return ordersMapper.getMoneyByID(order_id);
    }


}
