package cn.deliver.water.entity;

import lombok.*;

/**
 * @ClassName JmJson
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.10 下午 2:30
 * Version 1.0
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JmJson {

    private String sessionKey;

    private String encryptedData;

    private String iv;

    private String openid;
}
