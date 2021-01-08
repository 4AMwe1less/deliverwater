package cn.deliver.water.mapper;

import cn.deliver.water.entity.Express;
import cn.deliver.water.entity.Goods;
import cn.deliver.water.entity.GoodsPhotoes;
import cn.deliver.water.entity.OrderInfo;

import java.util.List;

/**
 * @ClassName AdminMapper
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.17 上午 11:07
 * Version 1.0
 **/
public interface AdminMapper {
    //管理员登录
    String getUpwdByUname(String uname);
    //管理员新增管理人员
    int insertAdminOther(String aid,String uname,String upwd);
    //查看所有订单
    List<OrderInfo> getAllOrder();
    //管理员新增配送人员
    int addExpresser(Express express);
    //查看所有配送人员
    List<Express> getAllExpresser();
    //管理员查看所有商品
    List<GoodsPhotoes> getAllGoods();
    //管理员查看某个商品详情
    Goods getOneGood(String gid);
    //管理人员删除配送人员
    int deleteExpresser(String eid);
    //管理员添加商品
    int addGoods(Goods goods);
    //删除商品
    int delGoods(String gid);
    //修改商品
    int updateGoods(String gname,String gdesc,String gstand,String gprice,String gid);
    //查询七天内的销售品类金额
    List<OrderInfo> getWeekOrder();
}
