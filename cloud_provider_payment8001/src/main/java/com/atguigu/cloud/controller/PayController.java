package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entity.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Monica
 * @version 1.0
 */
@RestController
@Slf4j
@Tag(name="支付微服务模块",description = "支付CRUD")
public class PayController {

    @Resource
    private PayService payService;

    @PostMapping("/pay/add")
    @Operation(summary = "新增", description = "新增支付流水方法,json串作为参数")
    public ResultData<String> addPay(@RequestBody Pay pay){
        payService.add(pay);
        return ResultData.success("新增成功");
    }

    @DeleteMapping("/pay/del/{id}")
    @Operation(summary = "根据id删除", description = "根据id删除支付流水方法")
    public ResultData<String> delPay(@PathVariable("id") Integer id){
        int i = payService.delete(id);
        return ResultData.success("删除成功");
    }

    @PutMapping("/pay/update")
    @Operation(summary = "修改", description = "修改支付流水方法,json串作为参数")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO){
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);
        payService.update(pay);
        return ResultData.success("修改成功");
    }

    @GetMapping(value = "/pay/get/{id}")
    @Operation(summary = "根据id查询", description = "根据id查询支付流水方法")
    public ResultData<Pay> getById(@PathVariable("id") Integer id){
        try {
            TimeUnit.SECONDS.sleep(62);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Pay data = payService.getById(id);
        return ResultData.success(data);
    }

    @GetMapping("pay/getAll")
    @Operation(summary = "查询支付列表", description = "查询支付列表流水方法")
    public ResultData<List<Pay>> getAll(){
        List<Pay> payList = payService.getAll();
        return ResultData.success(payList);
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/pay/get/info")
    public String getInfoByConsul(@Value("${atguigu.info}") String atguiguInfo){
        return "atguiguInfo:"+atguiguInfo+"\t"+"port: "+port;
    }
}
