package com.tcc.orderservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcc.orderservice.dao.OrderDao;
import com.tcc.orderservice.domain.UserOrder;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@Service
public class OrderService {

    @Resource
    private OrderDao orderDao;

    public void insertUserOrder(UserOrder userOrder) {
        orderDao.insertUserOrder(userOrder);
    }
}
