package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.apis.StorageFeignApi;
import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.mapper.OrderMapper;
import com.atguigu.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Monica
 * @version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource  // 订单微服务通过 openFeign 调用库存微服务
    private StorageFeignApi storageFeignApi;

    @Resource  // 订单微服务通过 openFeign 调用账户微服务
    private AccountFeignApi accountFeignApi;

    @Override
    @GlobalTransactional(name = "zzyy-create-order",rollbackFor = Exception.class) //AT
    public void create(Order order) {
        // xid 全局事务id的检查
        String xid = RootContext.getXID();
        // 1. 新建订单
        log.info("-------新建订单------xid:" + xid);
        // 订单新建时默认初始状态是 0-创建中
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);
        // 插入订单后获取插入 mysql 实体对象
        Order orderFormDB = null;
        if(result > 0){
            // 从 mysql 中查出刚插入的记录
            orderFormDB = orderMapper.selectOne(order);
            log.info("--------> 新建订单成功 orderFormDB info" + orderFormDB);
            System.out.println();
            // 2. 扣减库存
            log.info("--------> 库存微服务开始调用，扣减 account");
            storageFeignApi.decrease(orderFormDB.getProductId(), orderFormDB.getCount());
            log.info("--------> 库存微服务结束调用");
            // 3. 扣减账户
            log.info("--------> 账户微服务开始调用，扣减 money");
            accountFeignApi.decrease(orderFormDB.getUserId(), orderFormDB.getMoney());
            log.info("--------> 账户微服务结束调用");
            // 4. 修改订单状态, 1 - 已完结
            log.info("--------> 修改订单状态");
            orderFormDB.setStatus(1);

            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId", orderFormDB.getUserId());
            criteria.andEqualTo("status", 0);

            int updateResult = orderMapper.updateByExampleSelective(orderFormDB, whereCondition);
            System.out.println("-------> 修改订单完成 updateResult:" + updateResult);
            System.out.println("-------> orderFormDB info: " + orderFormDB);
        }
        System.out.println();
        log.info("-------结束新建订单----xid:" + xid);
    }
}
