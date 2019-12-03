package com.ly.cardadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

/**
 * @author Tarry
 * @create 2019/11/29 11:35
 */
@SpringBootApplication
@MapperScan("com.ly.cardadmin.mapper")
public class CardAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardAdminApplication.class,args);
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("100MB"); //KB,MB
        //设置总上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }
}
