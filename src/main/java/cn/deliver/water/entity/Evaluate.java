package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName Evaluate
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.15 下午 4:15
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evaluate {
    //商品评价
    //评价表id
    private int eid;
    //评价人id
    private String uid;
    //商品id
    private int gid;
    //评价内容
    private String econtent;
    //评价星级
    private int estars;
    //评价时间
    private String etime;


}
