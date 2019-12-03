package com.ly.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

/**
 * @author Tarry
 * @create 2019/11/29 9:18
 */
@SpringBootApplication
@MapperScan("com.ly.card.mapper")
public class CardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardApplication.class,args);
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
    @Bean
    public RestTemplate rest(){
        return new RestTemplate();
    }
}
