package com.bjmu.order.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "订单查询请求参数对象")
public class OrderQueryParam {

    private static final long serialVersionUID = -7021613361861360789L;

    private String msgId;
    private String msgSrc;
    private String msgType;
    private Date requestTimestamp;
    private String srcReserve;
    private String mid;
    private String tid;
    private String instMid;
    private String merOrderId;
    private String targetOrderId;
    private String sign;
    @ApiModelProperty(value = "查询标志（0-代表查询所有订单/1-代表查询所有未完成订单）",name = "flag")
    private String flag;
    private String userId;

}
