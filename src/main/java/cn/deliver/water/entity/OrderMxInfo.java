package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName OrderMxInfo
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.23 下午 2:03
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMxInfo {
    private String order_id;

    private String commodity_name;

    private String commodity_sl;

    private String commodity_dj;

    private String commodity_id;

    private String commodity_gg;

    private String imageurl;

    //private OrderInfo orderInfo;
}
