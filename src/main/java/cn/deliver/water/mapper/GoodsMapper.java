package cn.deliver.water.mapper;

import cn.deliver.water.entity.Evaluate;
import cn.deliver.water.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName GoodsMapper
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.16 上午 10:01
 * Version 1.0
 **/
@Mapper
public interface GoodsMapper {
    //查看所有商品
    List<Goods> getAllGoods();
    //查看某个商品所有评价
    List<Evaluate> getAllEvalute(int gid);
}
