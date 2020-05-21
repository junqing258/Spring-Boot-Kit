package org.inlighting;

//import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.annotation.ImportResource;
//import org.inlighting.dubbo.provider.service.IEchoService;

@ServletComponentScan
@EnableScheduling
@MapperScan(basePackages = {"org.inlighting.mapper"})
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
        // 加载dubbo xml配置文件启动
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");
//        context.start();
    }
}
