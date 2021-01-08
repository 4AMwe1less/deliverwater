package cn.deliver.water.mapper;

import cn.deliver.water.entity.Files;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName FileMapper
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.21 上午 10:00
 * Version 1.0
 **/
@Mapper
public interface FileMapper {
    List<Files> list(String gid);

    public int saveImg(String gid, String url, String createTime);

    public int delPhoto(String gid, int pid);

    int updateIsTou(String gid);
}
