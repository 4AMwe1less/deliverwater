package cn.deliver.water.service;

import cn.deliver.water.entity.GetUserMoneyInfo;

import java.util.List;
import java.util.Map;

public interface UserMoneyService {

    int updateMoney(String userid, String addMoney);

    int updateOrderNumber(String userid);

    String getUserFatherID(String userid);

    String getUserOrderNumber(String userid);

    String getMoney(String userid);

    List<GetUserMoneyInfo> usermoneylist(String uphone);

    String exclude_notMoney(String userid);

    int countUphone(String uphone);

    String byOrderidGetUserid(String orderid);

    String byOrderidGetOrderMoney(String orderid);

    Map<String, String> byOrderidGetIscoupon(String orderid);
}
