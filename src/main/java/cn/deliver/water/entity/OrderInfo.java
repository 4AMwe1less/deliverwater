package cn.deliver.water.entity;

import lombok.*;

import java.util.List;

/**
 * @ClassName OrderInfo
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.17 下午 2:50
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private String ID;
    private String user_id;
    private List<OrderMxInfo> orderMxInfos;
    private String order_state;
    private String order_money;
    private String order_type;
    private String order_submit_time;
    private String order_over_time;
    private String order_msg;
    private String delivery_address;
    private String sjr_name;
    private String sjr_phone;
    private String psr;
    private String psr_phone;
    private String remarks;
    private String cdk;
    ////////////////////////
    private String discount_money;
    private String iscoupon;
}
