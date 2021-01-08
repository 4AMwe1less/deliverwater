package cn.deliver.water.mapper;

import cn.deliver.water.entity.ShoppingCartInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ShoppingCartMapper.xml
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.27 下午 4:26
 * Version 1.0
 **/
@Mapper
public interface ShoppingCartMapper {
    int save(ShoppingCartInfo shoppingCartInfo);

    int del(String userid,String commodityID);

    List<ShoppingCartInfo> select(String userid);

    int update();
}
