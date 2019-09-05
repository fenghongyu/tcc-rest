package com.tcc.orderservice;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tcc.orderservice.domain.OrderState;
import com.tcc.orderservice.domain.UserOrder;
import com.tcc.orderservice.service.OrderService;
import com.tcc.orderservice.utils.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceApplicationTests {

    @Resource
    OrderService orderService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void insertUserOrder() {
        String txId = UUID.randomUUID().toString();

        UserOrder userOrder = new UserOrder();
        userOrder.setTxId(txId);
        userOrder.setUserId(123L);
        userOrder.setProductCode("tcc");
        userOrder.setQuantity(100);
        userOrder.setState(OrderState.ORDERED);
        userOrder.setVersion(1L);
        userOrder.setExpireTime(DateUtils.plusMinutes(new Date(), 30));
        userOrder.setCreateTime(new Date());
        orderService.insertUserOrder(userOrder);
    }

}
