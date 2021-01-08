package cn.deliver.water.entity;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfo {

    private String aid;

    private String addr;

    private String uid;

    private String phone;

    private String uname;

    private String createTime;


}
