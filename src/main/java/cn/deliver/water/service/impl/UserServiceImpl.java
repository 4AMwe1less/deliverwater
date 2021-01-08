package cn.deliver.water.service.impl;

import cn.deliver.water.entity.AddressInfo;
import cn.deliver.water.entity.Evaluate;
import cn.deliver.water.entity.Goods;
import cn.deliver.water.entity.UserInfo;
import cn.deliver.water.mapper.UserMapper;
import cn.deliver.water.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int register(UserInfo userInfo) {
        return userMapper.register(userInfo);
    }

    @Override
    public UserInfo login(String username, String password) {
        return userMapper.login(username,password);
    }

    @Override
    public List<AddressInfo> getAllAddress(String uid) {
        return userMapper.getAllAddress(uid);
    }

//    @Override
//    public int addEvaluate(Evaluate evaluate) {
//        return userMapper.addEvaluate(evaluate);
//    }
//
    @Override
    public List<Goods> getAllGoods() {
        return userMapper.getAllGoods();
    }

//    @Override
//    public List<Evaluate> getAllEvalute(int gid) {
//        return userMapper.getAllEvalute(gid);
//    }

    @Override
    public int addAddress(AddressInfo addressInfo) {
        return userMapper.addAddress(addressInfo);
    }

    @Override
    public int delAddress(String aid, String uid) {
        return userMapper.delAddress(aid, uid);
    }

    @Override
    public int updateAddress(String add, String phone, String aid, String uid,String uname) {
        return userMapper.updateAddress(add, phone, aid, uid, uname);
    }

    @Override
    public AddressInfo getAddr(String aid) {
        return userMapper.getAddr(aid);
    }

    @Override
    public int countOrder() {
        return userMapper.countOrder();
    }

    @Override
    public int setQrCode(String uid, String url) {
        return userMapper.setQrCode(uid, url);
    }

    @Override
    public int countQrCode(String uid) {
        return userMapper.countQrCode(uid);
    }

    @Override
    public String getQrCode(String uid) {
        return userMapper.getQrCode(uid);
    }

    @Override
    public int insertOpenidAndPhone(String phone, String openid) {
        return userMapper.insertOpenidAndPhone(phone, openid);
    }

    @Override
    public int countOpenid(String openid) {
        return userMapper.countOpenid(openid);
    }

    @Override
    public int countOrderMo(String openid) {
        return userMapper.countOrderMo(openid);
    }

    @Override
    public int insertSuperior(String Aopenid, String Bopenid) {
        return userMapper.insertSuperior(Aopenid, Bopenid);
    }

    @Override
    public int countSuperior(String Bopenid) {
        return userMapper.countSuperior(Bopenid);
    }

    @Override
    public int countLower(String Aopenid) {
        return userMapper.countLower(Aopenid);
    }

    @Override
    public int insertMoney(String openid) {
        return userMapper.insertMoney(openid);
    }

    @Override
    public int updateUphoneByopenid(String openid, String uphone) {
        return userMapper.updateUphoneByopenid(openid, uphone);
    }

    @Override
    public int countOpenidNull(String openid) {
        return userMapper.countOpenidNull(openid);
    }
}
