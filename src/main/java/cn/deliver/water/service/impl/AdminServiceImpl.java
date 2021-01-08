package cn.deliver.water.service.impl;

import cn.deliver.water.entity.Express;
import cn.deliver.water.entity.Goods;
import cn.deliver.water.entity.GoodsPhotoes;
import cn.deliver.water.entity.OrderInfo;
import cn.deliver.water.mapper.AdminMapper;
import cn.deliver.water.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AdminServiceImpl
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.17 上午 11:06
 * Version 1.0
 **/
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public String getUpwdByUname(String uname) {
        return adminMapper.getUpwdByUname(uname);
    }

    @Override
    public int insertAdminOther(String aid,String uname, String upwd) {
        return adminMapper.insertAdminOther(aid,uname, upwd);
    }

    @Override
    public List<OrderInfo> getAllOrder() {
        return adminMapper.getAllOrder();
    }

    @Override
    public int addExpresser(Express express) {
        return adminMapper.addExpresser(express);
    }

    @Override
    public List<Express> getAllExpresser() {
        return adminMapper.getAllExpresser();
    }

    @Override
    public List<GoodsPhotoes> getAllGoods() {
        return adminMapper.getAllGoods();
    }

    @Override
    public Goods getOneGood(String gid) {
        return adminMapper.getOneGood(gid);
    }

    @Override
    public int deleteExpresser(String eid) {
        return adminMapper.deleteExpresser(eid);
    }

    @Override
    public int addGoods(Goods goods) {
        return adminMapper.addGoods(goods);
    }

    @Override
    public int delGoods(String gid) {
        return adminMapper.delGoods(gid);
    }

    @Override
    public int updateGoods(String gname, String gdesc, String gstand, String gprice,String gid) {
        return adminMapper.updateGoods(gname, gdesc, gstand, gprice,gid);
    }

    @Override
    public List<OrderInfo> getWeekOrder() {
        return adminMapper.getWeekOrder();
    }
}
