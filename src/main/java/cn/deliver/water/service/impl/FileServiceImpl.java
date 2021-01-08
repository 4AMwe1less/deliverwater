package cn.deliver.water.service.impl;

import cn.deliver.water.entity.Files;
import cn.deliver.water.mapper.FileMapper;
import cn.deliver.water.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName FileServiceImpl
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.21 上午 9:59
 * Version 1.0
 **/
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Override
    public List<Files> list(String uid) {
        return fileMapper.list(uid);
    }

    @Override
    public int saveImg(String uid, String url, String createTime) {
        return fileMapper.saveImg(uid, url, createTime);
    }

    @Override
    public int delPhoto(String uid, int pid) {
        return fileMapper.delPhoto(uid, pid);
    }

    @Override
    public int updateIsTou(String gid) {
        return fileMapper.updateIsTou(gid);
    }
}
