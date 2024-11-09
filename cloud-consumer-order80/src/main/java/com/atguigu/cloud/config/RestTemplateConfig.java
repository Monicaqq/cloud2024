package com.atguigu.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Monica
 * @version 1.0
 * 提供多种便捷访问远程 Http 服务的方法
 * 是一种简单便捷的访问 restful 服务模板类，是 Spring 提供的用于访问 Rest 服务
 * 的 客户端模板工具集
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
