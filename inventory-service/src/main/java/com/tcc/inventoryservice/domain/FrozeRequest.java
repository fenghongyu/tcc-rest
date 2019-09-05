package com.tcc.inventoryservice.domain;

import java.util.Date;

import lombok.Data;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@Data
public class FrozeRequest {

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_HIDEN = 1;

    private String txId;

    private Integer status = 0;

    private String productCode;

    private Integer frozenNum;

    private Long version;

    private Date createTime;
}
