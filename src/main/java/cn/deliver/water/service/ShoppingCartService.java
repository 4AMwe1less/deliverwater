package cn.deliver.water.service;

import cn.deliver.water.entity.ShoppingCartInfo;

import java.util.List;

/**
 * @ClassName ShoppingCartService
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.27 下午 4:24
 * Version 1.0
 **/
public interface ShoppingCartService {

    int save(ShoppingCartInfo shoppingCartInfo);

    int del(String userid,String commodityID);

    List<ShoppingCartInfo> select(String userid);

    int update();
}
