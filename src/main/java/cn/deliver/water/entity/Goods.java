package cn.deliver.water.entity;

import lombok.*;

import java.util.List;

/**
 * @ClassName Goods
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.15 下午 4:18
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    //商品表
    private String gid;
    //商品名称
    private String gname;
    //商品描述
    private String gdesc;
    //商品规格
    private String gstand;
    //商品价格
    private String gprice;
    //创建时间
    private String createTime;
    //相册
    private List<Files> files;


}
