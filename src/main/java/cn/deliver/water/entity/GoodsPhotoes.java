package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName GoodsPhotoes
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.23 下午 1:35
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPhotoes {
    //商品表
    private String gid;
    //商品名称
    private String gname;
    //商品描述
    private String gdesc;
    //商品图片
    private String gphotoes;
    //商品规格
    private String gstand;
    //商品价格
    private String gprice;
    //创建时间
    private String createTime;
    private int pid;
    private String url;
}
