package com.tcc.tcccoordinatorcustomer;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tcc.tcccoordinatorcustomer.dto.UserOrder;
import com.tcc.tcccoordinatorcustomer.service.TccOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TccCoordinatorCustomerApplicationTests {

    @Autowired
    TccOrderService tccOrderService;

    public static final String PRODUCT_CODE = "tcc";
    public static final Long USER_ID = 123L;

    @Test
    public void testTccOrder() {
        String txId = UUID.randomUUID().toString();
        UserOrder orderRequest = UserOrder.builder()
                .userId(USER_ID)
                .productCode(PRODUCT_CODE)
                .quantity(10)
                .version(1L)
                .build();
        tccOrderService.newOrderWithTcc(orderRequest, txId);
    }

    @Test
    public void testTccOrderRetry() {
        String txId = "972c86d6-6bda-4859-9128-de10f4bf9634";
        UserOrder orderRequest = UserOrder.builder()
                .userId(USER_ID)
                .productCode(PRODUCT_CODE)
                .quantity(10)
                .build();
        tccOrderService.newOrderWithTcc(orderRequest, txId);
    }

    @Test
    public void testTccOrderRollback() {
        String txId = UUID.randomUUID().toString();
        UserOrder orderRequest = UserOrder.builder()
                .userId(USER_ID)
                .productCode(PRODUCT_CODE)
                .quantity(999)
                .build();
        tccOrderService.newOrderWithTcc(orderRequest, txId);
    }

}
