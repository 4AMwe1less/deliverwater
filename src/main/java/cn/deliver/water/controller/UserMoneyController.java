package cn.deliver.water.controller;

import cn.deliver.water.entity.GetUserMoneyInfo;
import cn.deliver.water.service.UserMoneyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserMoneyController {

    @Resource
    private UserMoneyService userMoneyService;

    /**
     * 提现修改余额
     *
     * @param userid 用户id
     * @param money  提现金额
     * @return
     */
    @PostMapping("admin/updateMoney")
    public Map<String,Object> updateMoney(String userid, String money) {
        Map<String,Object> data = new HashMap<>();
//        double not_money = Integer.valueOf(userMoneyService.exclude_notMoney(userid)) * 100;
        int rmoney = Integer.valueOf(money) ;//提现金额
        int umoney = 0;//用户余额

            umoney = Integer.valueOf(userMoneyService.getMoney(userid));//用户余额
            if (rmoney > umoney) {
                data.put("code",202);
                data.put("message","提现额度大于用户余额");
                return data;
            } else {
                rmoney = rmoney - rmoney * 2;
                if (userMoneyService.updateMoney(userid, String.valueOf(rmoney)) <= 1) {
                    data.put("code",200);
                    data.put("message","提现扣除成功");
                    return data;
                }
            }
        data.put("code",2105);
        data.put("message","提现失败，网络未知错误！");
        return data;
    }

    /**获得每个用户或单个用户的返利余额
     * @param uphone 用户手机号（不传查询所有）
     * @return 返回：userid username uphone money
     */
    @PostMapping("admin/getuserMoneyInfo")
    public Map<String,Object> list(@RequestParam(required = false) String uphone) {
        Map<String, Object> data = new HashMap<>();
        int i = userMoneyService.countUphone(uphone);
        if (i < 1) {
            data.put("code", 270);
            data.put("message", "该手机号不存在!");
            return data;
        } else {
            List<GetUserMoneyInfo> GetUserMoneyInfo = userMoneyService.usermoneylist(uphone);
            data.put("code", 200);
            data.put("data", GetUserMoneyInfo);
            data.put("message", "查询成功！");
            return data;
        }
    }

//    public static void main(String[] args) {
//        String aa = "100";
//        int bb = Integer.valueOf(aa);
//        int cc = Integer.valueOf("2")*100;
//        System.out.println(bb);
//        System.out.println(cc);
//       if (bb<cc){
//           System.out.println("大于");
//       }
//    }

}
