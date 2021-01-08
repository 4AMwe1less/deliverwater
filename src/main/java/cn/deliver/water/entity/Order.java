package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName Order
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.22 下午 2:35
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String commodity_name;
    private String order_money;
    private String order_count;

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }
}
