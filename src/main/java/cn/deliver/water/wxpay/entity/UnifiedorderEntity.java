package cn.deliver.water.wxpay.entity;

import org.springframework.stereotype.Component;



public class UnifiedorderEntity {



    //商品描述
    private String commodity_msg;

    //商品价格
    private int total_fee;

    //openID
    private String openid;

    //订单ID
    private String order_id;

    public String getCommodity_msg() {
        return commodity_msg;
    }

    public void setCommodity_msg(String commodity_msg) {
        this.commodity_msg = commodity_msg;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public UnifiedorderEntity() {
    }


}
