package com.tcc.inventoryservice.dao;

import org.apache.ibatis.annotations.Param;

import com.tcc.inventoryservice.domain.Inventory;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
public interface InventoryDao {

    int insertInventory(Inventory inventory);

    void updateInventoryLeftNum(@Param("id") Long id, @Param("leftNum") int leftNum);

    Inventory selectInventoryByProductCodeForUpdate(@Param("productCode") String productCode);

}
