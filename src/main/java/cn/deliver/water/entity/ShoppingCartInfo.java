package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName ShoppingCartInfo
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.27 下午 4:25
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartInfo {
    private String userid;

    private String commodityID;

    private String commodityName;

    private String commoditySL;

    private String commodityDJ;

    private String commodityGG;

    private String imgurl;

    private String createTime;

}
