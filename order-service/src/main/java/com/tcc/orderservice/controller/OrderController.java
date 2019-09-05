package com.tcc.orderservice.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.orderservice.dao.OrderDao;
import com.tcc.orderservice.domain.OrderState;
import com.tcc.orderservice.domain.UserOrder;
import com.tcc.orderservice.utils.DateUtils;
import com.tcc.tccrestparticipantapi.controller.TccParticipantController;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@RestController
@RequestMapping("/order")
public class OrderController extends TccParticipantController<UserOrder> {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private OrderDao orderDao;

    @Override
    public Object getParticipantName() {
        return "order-service";
    }

    @Override
    public ResponseEntity executeTry(String txId, UserOrder body) {
        try {

            body.setTxId(txId);
            body.setState(OrderState.ORDERED);
            body.setExpireTime(DateUtils.plusMinutes(new Date(), 30));
            body.setCreateTime(new Date());

            logger.info("{} order-controller begin to executeTry {}", txId, body);
            orderDao.insertUserOrder(body);
            logger.info("{} order-controller end to executeTry {}", txId, body);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @Override
    public ResponseEntity executeConfirm(String txId) {

        try {
            logger.info("{} order-controller begin to executeConfirm", txId);
            UserOrder userOrder = orderDao.selectUserOrderByTxId(txId);
            if(userOrder == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            orderDao.updateUserOrderStatus(OrderState.CONFIRMED, txId);
            logger.info("{} order-controller end to executeConfirm", txId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //订单确认时异常，怎么处理？返回 404 会导致不断重试么？？
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity executeCancel(String txId) {
        try {
            logger.info("{} order-controller begin to executeCancel", txId);
            UserOrder userOrder = orderDao.selectUserOrderByTxId(txId);
            if(userOrder == null) {
                //当前事务id的订单不存在，返回202，将不再继续发送请求？
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
            orderDao.updateUserOrderStatus(OrderState.CANCELED, txId);
            logger.info("{} order-controller end to executeCancel", txId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //订单确认时异常，怎么处理？返回 404 会导致不断重试么？？
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
