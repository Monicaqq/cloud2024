package com.atguigu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Monica
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Main3377
{
    public static void main(String[] args)
    {
        SpringApplication.run(Main3377.class,args);
    }
}