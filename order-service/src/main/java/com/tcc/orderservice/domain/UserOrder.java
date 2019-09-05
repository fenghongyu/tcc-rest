package com.tcc.orderservice.domain;

import java.util.Date;

import lombok.Data;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@Data
public class UserOrder {

    private Long id;

    private String txId;

    private Long userId;

    private String productCode;

    private Integer quantity;

    private OrderState state;

    private Date expireTime;

    private Long version;

    private Date createTime;
}
