package com.tcc.orderservice.dao;

import org.apache.ibatis.annotations.Param;

import com.tcc.orderservice.domain.OrderState;
import com.tcc.orderservice.domain.UserOrder;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
public interface OrderDao {
    int insertUserOrder(UserOrder userOrder);

    void updateUserOrderStatus(@Param("orderState") OrderState orderState, @Param("txId") String txId);

    UserOrder selectUserOrderByTxId(@Param("txId") String txId);


}
