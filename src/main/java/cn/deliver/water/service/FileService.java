package cn.deliver.water.service;


import cn.deliver.water.entity.Files;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileService {

        List<Files> list(String gid);

        public int saveImg(String gid,String url, String createTime);

        public int delPhoto(String gid, int pid);

        int updateIsTou(String gid);
    }


