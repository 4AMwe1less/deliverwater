package cn.deliver.water.entity;

/**
 * @ClassName Files
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.21 上午 10:01
 * Version 1.0
 **/


import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Files {
    private int pid;
    private String gid;
    private String url;
    private String createTime;
    private int status;
    }



