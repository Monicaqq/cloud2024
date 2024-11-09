package com.atguigu.cloud.service;

import org.apache.ibatis.annotations.Param;

/**
 * @author Monica
 * @version 1.0
 */
public interface AccountService {

    void decrease(@Param("userId") Long userId, Long money);
}
