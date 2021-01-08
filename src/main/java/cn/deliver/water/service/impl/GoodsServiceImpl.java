package cn.deliver.water.service.impl;

import cn.deliver.water.entity.Evaluate;
import cn.deliver.water.entity.Goods;
import cn.deliver.water.mapper.GoodsMapper;
import cn.deliver.water.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName GoodsServiceImpl
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.16 上午 10:02
 * Version 1.0
 **/
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.getAllGoods();
    }

    @Override
    public List<Evaluate> getAllEvalute(int gid) {
        return goodsMapper.getAllEvalute(gid);
    }
}
