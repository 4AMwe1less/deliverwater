package cn.deliver.water.controller;

import cn.deliver.water.entity.AddressInfo;
import cn.deliver.water.entity.Evaluate;
import cn.deliver.water.entity.Goods;
import cn.deliver.water.entity.UserInfo;
import cn.deliver.water.service.AdminService;
import cn.deliver.water.service.UserMoneyService;
import cn.deliver.water.service.UserService;
import cn.deliver.water.util.MD5Util;
import cn.deliver.water.util.RandomUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;
    @Resource
    private UserMoneyService userMoneyService;

    @PostMapping("/user/register")
    public String register(UserInfo userInfo) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        userInfo.setID(uuid);
        String pwd = MD5Util.string2MD5(userInfo.getPassword());
        userInfo.setPassword(pwd);
        int registerNumber = userService.register(userInfo);
        return String.valueOf(registerNumber);
    }


    //获取用户所有地址
    @PostMapping("user/getAllAddress")
    public Map<String, Object> getAllAddress(String uid) {
        List<AddressInfo> addList = userService.getAllAddress(uid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("message", "请查看用户所有的地址！");
        data.put("data", addList);
        return data;
    }

//    //添加评价
//    @PostMapping("user/addEvaluate")
//    public Map<String, Object> addEvaluate(int gid, String uid, String econtent, int estars) {
//        Evaluate evaluate = new Evaluate();
//        evaluate.setGid(gid);
//        evaluate.setUid(uid);
//        evaluate.setEcontent(econtent);
//        evaluate.setEstars(estars);
//        Date time = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String etime = sdf.format(time);
//        evaluate.setEtime(etime);
//        Map<String, Object> data = new HashMap<>();
//        int i = userService.addEvaluate(evaluate);
//        if (i > 0) {
//            data.put("code", 200);
//            data.put("message", "填写评价成功！");
//            return data;
//        } else {
//            data.put("code", 201);
//            data.put("message", "网络未知错误，评价失败");
//            return data;
//        }
//    }
//    //查看商品评价
//    @PostMapping("user/getAllEvaluate")
//    public Map<String,Object> getAllEvaluate(int gid){
//        List<Evaluate> eList = userService.getAllEvalute(gid);
//        Map<String,Object> data = new HashMap<>();
//        data.put("code",200);
//        data.put("data",eList);
//        data.put("message","请查看商品评价！");
//        return data;
//    }

    //查看所有商品
    @PostMapping("user/getAllGoods")
    public Map<String, Object> getAllGoods() {
        List<Goods> gList = userService.getAllGoods();
        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("data", gList);
        data.put("message", "请查看所有商品！");
        return data;
    }

    //添加地址
    @PostMapping("user/addAddress")
    public Map<String, Object> addAddress(String uid, String phone, String add, String uname) {
        Map<String, Object> data = new HashMap<>();
        AddressInfo addressInfo = new AddressInfo();
        String aid = UUID.randomUUID().toString().replace("-", "");
        addressInfo.setAid(aid);
        addressInfo.setAddr(add);
        addressInfo.setPhone(phone);
        addressInfo.setUid(uid);
        addressInfo.setUname(uname);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String createTime = sdf.format(date);
        addressInfo.setCreateTime(createTime);
        int i = userService.addAddress(addressInfo);
        if (i > 0) {
            data.put("code", 200);
            data.put("message", "成功！");
            return data;
        } else {
            data.put("code", 201);
            data.put("message", "添加失败，网络未知错误！");
            return data;
        }
    }

    //删除地址
    @PostMapping("user/delAddress")
    public Map<String, Object> delAddress(String aid, String uid) {
        Map<String, Object> data = new HashMap<>();
        int i = userService.delAddress(aid, uid);
        if (i > 0) {
            data.put("code", 200);
            data.put("message", "成功！");
            return data;
        } else {
            data.put("code", 201);
            data.put("message", "删除失败，网络未知错误！");
            return data;
        }
    }

    @PostMapping("user/updateAddress")
    public Map<String, Object> updateAddress(String aid, String uid, @RequestParam(required = false) String phone, String add, String uname) {
        Map<String, Object> data = new HashMap<>();
        int i = userService.updateAddress(add, phone, aid, uid, uname);
        if (i > 0) {
            data.put("code", 200);
            data.put("message", "成功！");
            return data;
        } else {
            data.put("code", 201);
            data.put("message", "修改失败，网络未知错误！");
            return data;
        }
    }

    //查看某个特定地址
    @PostMapping("user/getOneAddr")
    public Map<String, Object> getOneAddr(String aid) {
        AddressInfo addressInfo = userService.getAddr(aid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 200);
        data.put("data", addressInfo);
        data.put("message", "请查看地址！");
        return data;
    }

    @Scheduled(cron = "0 */30 8-18 * * ?")
    @PostMapping("user/sendMsg")
    public Map<String, Object> sendMsg() throws Exception {
        Map<String, Object> data = new HashMap<>();
        int number = userService.countOrder();
        if (number > 0) {
            //设置超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            // 初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GHNsPhkCNV6T7vuMpr5", "zpGQK9w6bOf00eyijIWs1TnsYqhSXp");
            IAcsClient acsClient = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            request.setAction("SendSms");
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", "15628788995");
            request.putQueryParameter("SignName", "月老的线");
            request.putQueryParameter("TemplateCode", "SMS_203675168");
            request.putQueryParameter("TemplateParam", "{number:" + number + "}");
            try {
                CommonResponse response = acsClient.getCommonResponse(request);
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
            }
            data.put("code", 200);
            data.put("message", "发送成功！");
            return data;
        } else {
            data.put("code",205);
            data.put("message","暂无未处理的订单");
            return data;
        }
    }
    @PostMapping("user/scanQrCode")
    public Map<String, Object> SacnQrCode(String Aopenid, String Bopenid) {
        Map<String, Object> data = new HashMap<>();
        int j = userService.countSuperior(Bopenid);
        if (j > 0) {
            data.put("code", 205);
            data.put("message", "您已经扫过其他人的二维码了！");
            return data;
        } else {
            int i = userService.insertSuperior(Aopenid, Bopenid);
            if (i > 0) {
                data.put("code", 200);
                data.put("message", "设置上家成功！");
                return data;
            } else {
                data.put("code", 201);
                data.put("message", "设置上家失败！！");
                return data;
            }
        }
    }
}