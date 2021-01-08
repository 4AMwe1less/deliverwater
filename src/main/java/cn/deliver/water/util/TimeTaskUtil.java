package cn.deliver.water.util;

import cn.deliver.water.service.MyRedis;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.ws.RequestWrapper;

/**
 * @ClassName TimeTaskUtil
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.13 下午 2:21
 * Version 1.0
 **/

@Component
public class TimeTaskUtil {

//    //每一个半小时刷新accesstoken
//    @Scheduled(cron = "0 */90 * * * ?")
//    public void access_token(){
//
//    }
//
//    //每半小时发送未处理订单个数
//    @Scheduled(cron = "0 */30 * * * ?")
//    public void unOrder(){
//
//    }
}
