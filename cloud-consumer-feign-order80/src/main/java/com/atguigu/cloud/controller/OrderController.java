package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entity.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * @author Monica
 * @version 1.0
 */
@RestController
public class OrderController {

    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        return payFeignApi.addPay(payDTO);
    }

    @GetMapping("/feign/pay/info/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        ResultData resultData = null;
        try{
            System.out.println("开始时间:" + DateUtil.now());
            resultData = payFeignApi.getPayInfo(id);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("结束时间:" + DateUtil.now());
            ResultData.fail(ReturnCodeEnum.RC500.getCode(),e.getMessage());
        }
        return resultData;
    }

    @GetMapping("/feign/pay/mylb")
    public String payMylb(){
        return payFeignApi.myLoadBalance();

    }
}
