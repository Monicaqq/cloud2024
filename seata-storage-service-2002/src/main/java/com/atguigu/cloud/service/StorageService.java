package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Storage;

/**
 * @author Monica
 * @version 1.0
 */
public interface StorageService {
    void decrease(Long productId, Integer count);
}
