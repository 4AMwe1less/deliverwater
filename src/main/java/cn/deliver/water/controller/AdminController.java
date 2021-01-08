package cn.deliver.water.controller;

import cn.deliver.water.entity.*;
import cn.deliver.water.service.AdminService;
import cn.deliver.water.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.17 上午 11:15
 * Version 1.0
 **/
@RestController
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private FileService fileService;

    //管理员登录
    @PostMapping("admin/login")
    public Map<String,Object> login(String uname,String upwd){
        Map<String,Object> data = new HashMap<>();
        String upwdGet = adminService.getUpwdByUname(uname);
        if (upwd.equals(upwdGet)){
            data.put("code",200);
            data.put("message","登录成功！");
            data.put("data",uname);
            return data;
        }else{
            data.put("code",201);
            data.put("message", "登录失败，用户名或密码错误！");
            return data;
        }
    }

    //添加管理人员
    @PostMapping("admin/addAdmin")
    public Map<String,Object> addAdmin(String uname,String upwd){
        Map<String,Object> data = new HashMap<>();
        String aid = UUID.randomUUID().toString().replaceAll("-", "");
       int i = adminService.insertAdminOther(aid,uname, upwd);
        if (i>0){
            data.put("code",200);
            data.put("message","添加管理人员成功！");
            return data;
        }else {
            data.put("code",201);
            data.put("message","网络未知错误，添加失败！");
            return data;
        }
    }
    //查看全部订单
    @PostMapping("admin/selectAllOrder")
    public Map<String,Object> selectAllOrder(){
        Map<String,Object> data = new HashMap<>();
        List<OrderInfo> oList = adminService.getAllOrder();
        data.put("code", 200);
        data.put("message","请查看所有订单");
        data.put("data",oList);
        return data;
    }
    //添加配送人员
    @PostMapping("admin/addExpresser")
    public Map<String,Object> addExpresser(String uname,String ephone){
        Map<String,Object> data = new HashMap<>();
        Express express = new Express();
        String eid = UUID.randomUUID().toString().replaceAll("-", "");
        express.setEid(eid);
        express.setEname(uname);
        express.setEphone(ephone);
        adminService.addExpresser(express);
        data.put("code", 200);
        data.put("message","添加配送人员完成！");
        return data;
    }
    //查看全部配送人员
    @PostMapping("admin/getAllExpresser")
    public Map<String,Object> getAllExpresser(){
        Map<String,Object> data = new HashMap<>();
        List<Express> eList = adminService.getAllExpresser();
        data.put("code",200);
        data.put("data",eList);
        data.put("message","请查看所有配送人员！");
        return data;
    }
//查看全部商品
    @PostMapping("admin/getAllGoods")
    public Map<String,Object> getAllGoods(){
        Map<String,Object> data = new HashMap<>();
        List<GoodsPhotoes> gList = adminService.getAllGoods();
        data.put("code",200);
        data.put("message", "请查看所有商品");
        data.put("data",gList);
        return data;
    }
    //查看某个商品详情
    @PostMapping("admin/getOneGood")
    public Map<String,Object> getOneGood(String gid){
        Map<String,Object> data = new HashMap<>();
        Goods goods = adminService.getOneGood(gid);
        List<Files> fList = fileService.list(gid);
        goods.setFiles(fList);
//        Map<String,Object> maps = new HashMap<>();
//        Map<String,Object> map = new HashMap<>();
//        maps.put("fList",fList);
//        maps.put("goods",goods);
        data.put("code",200);
        data.put("message", "请查看该商品详情!");
        data.put("data",goods);
        return data;
    }
    //删除配送人员
    @PostMapping("admin/delExpress")
    public Map<String,Object> delExpress(String eid){
        Map<String,Object> data = new HashMap<>();
        int i = adminService.deleteExpresser(eid);
        if (i>0){
            data.put("code",200);
            data.put("message","删除成功！");
            return data;
        }else {
            data.put("code",201);
            data.put("message","删除失败,网络位置错误！");
            return data;
        }
    }
    //添加商品
    @PostMapping("admin/addGoods")
    public Map<String,Object> addGoods(String gname,String gdesc,String gprice,String gstand){
        Map<String,Object> data = new HashMap<>();
        String gid = UUID.randomUUID().toString().replaceAll("-", "");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String createTime= formatter.format(date);
        Goods goods = new Goods();
        goods.setGid(gid);
        goods.setGstand(gstand);
        goods.setGdesc(gdesc);
        goods.setGname(gname);
        goods.setGprice(gprice);
        goods.setCreateTime(createTime);
        int i = adminService.addGoods(goods);
        if (i>0){
            data.put("code",200);
            data.put("data",gid);
            data.put("message","添加成功！");
            return data;
        }else{
            data.put("code",201);
            data.put("message","添加失败，网络未知错误！");
            return data;
        }
    }

//删除商品
@PostMapping("/admin/delGoods")
    public Map<String,Object> delGoods(String gid){
    Map<String,Object> data = new HashMap<>();
    int i = adminService.delGoods(gid);
    if (i>0){
        data.put("code",200);
        data.put("message","删除成功！");
        return data;
    }else{
        data.put("code",201);
        data.put("message","删除失败，网络未知错误！");
        return data;
    }
}
//修改商品
    @PostMapping("admin/updateGoods")
    public Map<String,Object> updateGoods(String gname,String gdesc,String gstand,String gprice,String gid){
        Map<String,Object> data = new HashMap<>();
        int i = adminService.updateGoods(gname, gdesc, gstand, gprice,gid);
        if (i>0){
            data.put("code",200);
            data.put("message","修改成功！");
            return data;
        }else{
            data.put("code",201);
            data.put("message","修改失败，网络未知错误！");
            return data;
        }
    }

//    public static void main(String[] args) {
//        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        System.out.println(list);
//        String str = StringUtils.join(list.toArray(),",");
//        System.out.println(str);
//        String[] strArr = str.split(",");
//        for (int i =0;i<strArr.length;i++){
//            System.out.println(strArr[i]);
//        }
//    }
//public static void main(String[] args) {
//    String cdk = RandomUtils.Random();
//    System.out.println(cdk);
//}
//public static void main(String[] args) {
//    String uuid = UUID.randomUUID().toString().replaceAll("-","");
//    uuid = uuid.substring(0,uuid.length()-24);
//    System.out.println(uuid);
//}
}
