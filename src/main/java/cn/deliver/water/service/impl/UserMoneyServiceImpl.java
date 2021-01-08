package cn.deliver.water.service.impl;

import cn.deliver.water.entity.GetUserMoneyInfo;
import cn.deliver.water.mapper.UserMoneyMapper;
import cn.deliver.water.service.UserMoneyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserMoneyServiceImpl implements UserMoneyService {

    @Resource
    private UserMoneyMapper userMoneyMapper;


    @Override
    public synchronized int updateMoney(String userid, String addMoney) {
        return userMoneyMapper.updateMoney(userid, addMoney);
    }

    @Override
    public synchronized int updateOrderNumber(String userid) {
        return userMoneyMapper.updateOrderNumber(userid);
    }

    @Override
    public synchronized String getUserFatherID(String userid) {
        return userMoneyMapper.getUserFatherID(userid);
    }

    @Override
    public synchronized String getUserOrderNumber(String userid) {
        return userMoneyMapper.getUserOrderNumber(userid);
    }

    @Override
    public String getMoney(String userid) {
        return userMoneyMapper.getMoney(userid);
    }

    @Override
    public List<GetUserMoneyInfo> usermoneylist(String uphone) {
        return userMoneyMapper.usermoneylist(uphone);
    }

    @Override
    public String exclude_notMoney(String userid) {
        return userMoneyMapper.exclude_notMoney(userid);
    }

    @Override
    public int countUphone(String uphone) {
        return userMoneyMapper.countUphone(uphone);
    }

    @Override
    public String byOrderidGetUserid(String orderid) {
        return userMoneyMapper.byOrderidGetUserid(orderid);
    }

    @Override
    public String byOrderidGetOrderMoney(String orderid) {
        return userMoneyMapper.byOrderidGetOrderMoney(orderid);
    }

    @Override
    public Map<String, String> byOrderidGetIscoupon(String orderid) {
        return userMoneyMapper.byOrderidGetIscoupon(orderid);
    }
}