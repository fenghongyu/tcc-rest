package com.tcc.inventoryservice.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Inventory {
    private Long id;
    private String productCode;
    private Integer leftNum; //库存数量
    private Long version;
    private Date createTime;

}
