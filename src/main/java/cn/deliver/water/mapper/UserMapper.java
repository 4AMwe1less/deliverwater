package cn.deliver.water.mapper;

import cn.deliver.water.entity.AddressInfo;
import cn.deliver.water.entity.Evaluate;
import cn.deliver.water.entity.Goods;
import cn.deliver.water.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int register(UserInfo userInfo);

    UserInfo login(String username,String password);
    //用户查看自己所有的地址
    List<AddressInfo> getAllAddress(String uid);
//    //用户对订单填写评价
//    int addEvaluate(Evaluate evaluate);
//    //查看某个商品所有评价
//    List<Evaluate> getAllEvalute(int gid);
    //用户新增地址
    int addAddress(AddressInfo addressInfo);
    //删除地址
    int delAddress(String aid,String uid);
    //修改地址
    int updateAddress(String add,String phone,String aid,String uid,String uname);
    //用户获取特定地址
    AddressInfo getAddr(String aid);
    List<Goods> getAllGoods();
    //查询未处理订单的个数
    int countOrder();
    //保存用户二维码
    int setQrCode(String uid,String url);
    //查看用户是否获取过二维码
    int countQrCode(String uid);
    //查看用户的二维码
    String getQrCode(String uid);
    //将用户的openid和手机号存储到数据库
    int insertOpenidAndPhone(String phone,String openid);
    //查看用户是否登录过
    int countOpenid(String openid);
    //查看用户有没有下单过
    int countOrderMo(String openid);
    //用户扫描二维码设置上家
    int insertSuperior(String Aopenid,String Bopenid);
    //查看用户是否拥有上家
    int countSuperior(String Bopenid);
    //查看用户的下家
    int countLower(String Aopenid);
    //初始化金额表
    int insertMoney(String openid);
    //根据openid修改uphone
    int updateUphoneByopenid(String openid,String uphone);
    //查找user表里是否存在此id
    int countOpenidNull(String openid);




}
