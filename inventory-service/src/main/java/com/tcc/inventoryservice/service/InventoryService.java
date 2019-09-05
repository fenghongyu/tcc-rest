package com.tcc.inventoryservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcc.inventoryservice.dao.InventoryDao;
import com.tcc.inventoryservice.domain.Inventory;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@Service
public class InventoryService {

    @Resource
    private InventoryDao inventoryDao;

    public void insertUserOrder(Inventory inventory) {
        inventoryDao.insertInventory(inventory);
    }
}
