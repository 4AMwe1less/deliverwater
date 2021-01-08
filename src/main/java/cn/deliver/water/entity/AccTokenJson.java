package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName AccTokenJson
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.09 下午 3:45
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccTokenJson {
    private String access_token;

    private String expires_in;

}
