package cn.deliver.water.service;

import cn.deliver.water.entity.Evaluate;
import cn.deliver.water.entity.Goods;

import java.util.List;

public interface GoodsService {
    //查看所有商品
    List<Goods> getAllGoods();
    //查看某个商品所有评价
    List<Evaluate> getAllEvalute(int gid);

}
