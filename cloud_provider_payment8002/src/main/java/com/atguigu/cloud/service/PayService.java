package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Pay;

import java.util.List;

/**
  * @author Monica
  * @version 1.0
  */
public interface PayService {
    int add(Pay pay);
    int delete(Integer id);
    int update(Pay pay);

    Pay getById(Integer id);
    List<Pay> getAll();
}
