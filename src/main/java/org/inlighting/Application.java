package org.inlighting;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.annotation.ImportResource;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

import org.inlighting.dubbo.provider.service.IEchoService;

@ServletComponentScan
@MapperScan(basePackages = {"org.inlighting.mapper"})
@EnableDubboConfiguration
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
        // 加载dubbo xml配置文件启动
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");
//        context.start();
    }
}
