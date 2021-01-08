package cn.deliver.water.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName WebXmlConfig
 * @Description TODO
 * @Author J1angHao
 * Date 2020.10.14 上午 9:58
 * Version 1.0
 **/
@Configuration
public class WebXmlConfig extends WebMvcConfigurerAdapter {
    @Value("${file.uploadFlinux}")
    private String uploadLinux;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //文件上传路径(Linxu)
        System.out.println(uploadLinux);
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/home/package/Yuelao/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/home/package/Yuelao/upload/");
        super.addResourceHandlers(registry);
    }
}
