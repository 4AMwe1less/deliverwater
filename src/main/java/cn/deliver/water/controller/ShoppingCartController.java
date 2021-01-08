package cn.deliver.water.controller;

import cn.deliver.water.entity.ShoppingCartInfo;
import cn.deliver.water.service.ShoppingCartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ShoppingCartController
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.27 下午 4:29
 * Version 1.0
 **/
@RestController
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;


    /**
     * 添加商品
     *
     * @param shoppingCartInfo 购物车实体（imgurl不用传！）
     * @return 添加成功或失败
     */
    @PostMapping("/cart/save")
    public Map<String,Object> save(@RequestBody ShoppingCartInfo shoppingCartInfo) {
        Map<String,Object> data = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String CreateTime = sdf.format(date);
        shoppingCartInfo.setCreateTime(CreateTime);
        if (shoppingCartService.save(shoppingCartInfo) >= 1) {
            data.put("code",200);
            data.put("message","添加成功");
            return data;
        } else {
            data.put("code",201);
            data.put("message","添加失败");
            return data;
        }

    }

    /**
     * @param userid 用户id
     * @return 购物车list
     */
    @PostMapping("cart/select")
    public Map<String,Object> select(String userid) {
        Map<String,Object> data = new HashMap<>();
        List<ShoppingCartInfo> list = shoppingCartService.select(userid);
        data.put("code",200);
        data.put("data",list);
        data.put("message","查询成功！");
        return data;
    }


    /**
     * 删除购物车某件商品
     *
     * @param userid      用户id
     * @param commodityID 商品id
     * @return 删除成功或失败
     */
    @PostMapping("/cart/del")
    public Map<String,Object> del(String userid, String commodityID) {
        Map<String,Object> data = new HashMap<>();
        if (shoppingCartService.del(userid, commodityID) >= 1) {
            data.put("code",200);
            data.put("message","删除成功");
            return data;
        } else {
            data.put("code",201);
            data.put("message","删除失败");
            return data;

        }
    }


}
