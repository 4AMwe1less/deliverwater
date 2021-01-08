package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName Express
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.17 下午 5:12
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Express {
    private String eid;
    private String ename;
    private String ephone;
    private int status;
}
