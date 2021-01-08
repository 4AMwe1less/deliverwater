
package cn.deliver.water.controller;

import cn.deliver.water.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
    @RequestMapping
    public class UploadController{
        @Resource
        private FileService fileservice;

        private Logger logger = LoggerFactory.getLogger(UploadController.class);


        @RequestMapping("uploads")
        @ResponseBody
        public Map<String,Object> upload( @RequestParam("file") MultipartFile content, HttpServletRequest request,String gid ) throws IOException {
            Map<String, Object> data = new HashMap<>();
            List list = new ArrayList();
            System.out.println(content.getOriginalFilename());
            System.out.println("执行upload");
            logger.info("执行图片上传");
            if (!content.isEmpty()) {
                logger.info("成功获取照片");
                String fileName = content.getOriginalFilename();
                String url = null;
                String type = null;
                String realUrl = null;
                type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
                logger.info("图片初始名称为：" + fileName + " 类型为：" + type);
                if (type != null) {
                    if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                        // 保存路径
                        String realPath = "/home/package/Yuelao/upload/";
//                        String realPath = "D://upload";
                        // 自定义的文件名称
                        String trueFileName = fileName;
                        //设置存放图片文件的路径
                        url = realPath + trueFileName;
                        System.out.println(url);
                        logger.info("存放图片文件的路径:" + url);
                        content.transferTo(new File(url));
                        logger.info("文件成功上传到指定目录下");
                        Date time = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                        String createTime = sdf.format(time);
                        realUrl ="https://www.jinghexinxi.top/other/upload/"+fileName;
                        int i = fileservice.saveImg(gid, realUrl, createTime);
//                        fileservice.updateIsTou(gid);
//                        realUrl = "https://www.jinghexinxi.top/behind/upload/"+fileName;
//                        int i = fileservice.saveImg(uid,realUrl,createTime);
//                        if (i>0){
//                            data.put("code",200);
//                            data.put("message","图片上传成功！");
//                            return data;
//                        }
                    } else {
                        logger.info("不是我们想要的文件类型,请按要求重新上传");
                        data.put("code", 101);
                        data.put("message", "文件类型不正确！");
                        return data;
                    }
                } else {
                    logger.info("文件类型为空");
                    data.put("code", 102);
                    data.put("message", "文件为空！");
                    return data;
                }
            } else {
                logger.info("没有找到相对应的文件");
                data.put("code", 103);
                data.put("message", "没找到相应的文件！");
                return data;
            }

            data.put("code",200);
            data.put("message","成功！");
            return data;
//        return "success";
        }
    }




