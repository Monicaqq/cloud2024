package com.atguigu.cloud.controller;

import com.atguigu.cloud.entity.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Monica
 * @version 1.0
 */
@RestController
public class OrderController {
    // 需要调用的远程微服务 支付服务的地址
    public static final String PaymentSrv_URL = "http://cloud-payment-service";//服务注册中心上的微服务名称
    //public static final String PaymentSrv_URL = "http://localhost:8001";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        // Rest 请求地址
        // 请求参数
        // HTTP 响应转换被转换成的对象类型
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add", payDTO, ResultData.class);
    }

    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfoById(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/" + id, ResultData.class, id);
    }

    @GetMapping("/consumer/pay/getAll")
    public ResultData getPayList(){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/getAll", ResultData.class);
    }

    @PutMapping("/consumer/pay/update")
    public ResultData updateOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/update", payDTO, ResultData.class);
    }

    @DeleteMapping("/consumer/pay/del/{id}")
    public ResultData delOrder(@PathVariable("id") Integer id){
        restTemplate.delete(PaymentSrv_URL + "/pay/del/{id}",id);
        return ResultData.success("删除成功");
    }

    @GetMapping("/consumer/pay/get/info")
    public String getInfoByConsul(){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
    }

    @Resource
    public DiscoveryClient discoveryClient;

    @GetMapping("/consumer/discovery")
    public String discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }
        System.out.println("============");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId()+"\t"+instance.getHost()
            +"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }
}
