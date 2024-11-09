package com.atguigu.cloud.controller;

import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Monica
 * @version 1.0
 */
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping("/account/decrease")
    public ResultData decrease(@RequestParam("userId") Long userId, @RequestParam("money") Long money){
        accountService.decrease(userId, money);
        return ResultData.success("扣减余额成功");
    }
}
