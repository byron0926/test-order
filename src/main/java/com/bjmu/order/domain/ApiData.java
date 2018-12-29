package com.bjmu.order.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApiData {

    private String sno;
    private String merchantCode;
    private String orderNo;
    private String remark;
    private BigDecimal amount;
    private String clientIp;


}
