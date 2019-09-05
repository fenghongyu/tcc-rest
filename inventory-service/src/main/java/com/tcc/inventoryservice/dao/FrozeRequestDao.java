package com.tcc.inventoryservice.dao;

import org.apache.ibatis.annotations.Param;

import com.tcc.inventoryservice.domain.FrozeRequest;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
public interface FrozeRequestDao {

    int insertFrozeRequest(FrozeRequest frozeRequest);

    void updateFrozeRequestStatus(@Param("txId") String txId, @Param("status") int status);

    FrozeRequest selectFrozeRequest(@Param("txId") String txId, @Param("status") int status);
}
