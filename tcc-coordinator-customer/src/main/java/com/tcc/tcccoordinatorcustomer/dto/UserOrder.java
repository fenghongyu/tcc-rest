package com.tcc.tcccoordinatorcustomer.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@Data
@Builder
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
