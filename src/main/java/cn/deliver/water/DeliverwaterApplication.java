package cn.deliver.water;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "cn.deliver.water.mapper")
@EnableScheduling
public class DeliverwaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliverwaterApplication.class, args);
    }

}
