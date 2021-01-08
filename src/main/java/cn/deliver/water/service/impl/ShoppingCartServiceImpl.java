package cn.deliver.water.service.impl;

import cn.deliver.water.entity.ShoppingCartInfo;
import cn.deliver.water.mapper.ShoppingCartMapper;
import cn.deliver.water.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ShoppingCartServiceImpl
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.27 下午 4:27
 * Version 1.0
 **/
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public int save(ShoppingCartInfo shoppingCartInfo) {
        return shoppingCartMapper.save(shoppingCartInfo);
    }

    @Override
    public int del(String userid, String commodityID) {
        return shoppingCartMapper.del(userid, commodityID);
    }

    @Override
    public List<ShoppingCartInfo> select(String userid) {
        return shoppingCartMapper.select(userid);
    }

    @Override
    public int update() {
        return shoppingCartMapper.update();
    }
}
