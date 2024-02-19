package com.tian.my_qa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 映射资源-重写WebMvcConfigurer接口，实现对资源的映射
// 例如有一个url：http://localhost:8080/images/Hello.jpg
// 表示要对Hello.jpg进行请求访问，这时候服务器会把这个请求的资源映射到我们配置的路径之下
// 也就是会到 fileSavePath 下去寻找 是否有 Hello.jpg 这个资源，返回给前端页面。

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file-save-path}")
    private String fileSavePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + fileSavePath);
    }
}
