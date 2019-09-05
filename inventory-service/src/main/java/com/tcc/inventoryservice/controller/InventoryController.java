package com.tcc.inventoryservice.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.inventoryservice.dao.FrozeRequestDao;
import com.tcc.inventoryservice.dao.InventoryDao;
import com.tcc.inventoryservice.domain.FrozeRequest;
import com.tcc.inventoryservice.domain.Inventory;
import com.tcc.tccrestparticipantapi.controller.TccParticipantController;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController extends TccParticipantController<FrozeRequest> {
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Resource
    private InventoryDao inventoryDao;
    @Resource
    private FrozeRequestDao frozeRequestDao;

    @Override
    public Object getParticipantName() {
        return "inventory-service";
    }

    @Override
    public ResponseEntity executeTry(String txId, FrozeRequest body) {
        try {
            logger.info("inventory-controller {} begin to executeTry{}", txId, body);
            Inventory inventory = inventoryDao.selectInventoryByProductCodeForUpdate(body.getProductCode());
            if (inventory == null) {
                return ResponseEntity.notFound().build();
            }
            if (inventory.getLeftNum() < body.getFrozenNum()) {
                //TODO 库存不够，应当返回明确信息，告知库存不够，而不是404,
                return ResponseEntity.notFound().build();
            }
            //找出已存在的库存冻结订单
            FrozeRequest request = frozeRequestDao.selectFrozeRequest(txId, FrozeRequest.STATUS_NORMAL);
            if (request != null) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            body.setTxId(txId);
            body.setCreateTime(new Date());
            body.setStatus(FrozeRequest.STATUS_NORMAL);
            frozeRequestDao.insertFrozeRequest(body);
            logger.info("inventory-controller {} end to executeTry{}", txId, body);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity executeConfirm(String txId) {
        try {
            logger.info("inventory-controller {} begin to executeConfirm", txId);
            FrozeRequest frozeRequest = frozeRequestDao.selectFrozeRequest(txId, FrozeRequest.STATUS_NORMAL);
            if(frozeRequest == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            Inventory inventory = inventoryDao.selectInventoryByProductCodeForUpdate(frozeRequest.getProductCode());
            if(inventory == null) {
                return ResponseEntity.notFound().build();
            }
            frozeRequestDao.updateFrozeRequestStatus(txId, FrozeRequest.STATUS_HIDEN);
            int left = inventory.getLeftNum() - frozeRequest.getFrozenNum();
            if(left < 0) {
                throw new IllegalStateException("inventory left < 0");
            }
            inventoryDao.updateInventoryLeftNum(inventory.getId(), left);
            logger.info("inventory-controller {} end to executeConfirm", txId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalStateException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity executeCancel(String txId) {
        try {
            logger.info("inventory-controller {} begin to executeCancel", txId);
            FrozeRequest frozeRequest = frozeRequestDao.selectFrozeRequest(txId, FrozeRequest.STATUS_NORMAL);
            if(frozeRequest == null) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
            Inventory inventory = inventoryDao.selectInventoryByProductCodeForUpdate(frozeRequest.getProductCode());
            if(inventory == null) {
                return ResponseEntity.notFound().build();
            }

            frozeRequestDao.updateFrozeRequestStatus(txId, FrozeRequest.STATUS_HIDEN);
            logger.info("inventory-controller {} end to executeCancel", txId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
