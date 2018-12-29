package com.bjmu.order.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private Long id;
    private String sno;
    private String merchantNo;
    private String orderNo;
    private String remark;
    private BigDecimal amount;
    private String clientIp;
    private String reqTime;//请求支付时间
    //订单状态(1-代表接收到请求支付/2-代表已转发请求/3-代表支付中/4-支付完成并关闭订单/5-支付超时并关闭订单)
    private int orderStatus;
    private String crtTime;//订单创建时间
    private String updTime;//订单更新时间

}
