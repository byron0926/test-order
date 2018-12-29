package com.bjmu.order.domain;

import lombok.Data;

@Data
public class RespStr {
    private String msgId;
    private String msgType;
    private String msgSrc;
    private String srcReserve;
    private String responseTimestamp;
    private String merName;
    private String merOrderId;
    private String mid;
    private String tid;
    private String settleRefId;
    private String Status;
    private Long totalAmount;
    private String targetOrderId;
    private String targetSys;
    private String targetStatus;
//    private Map<String,Object> jsPayRequest;
//    private Map<String,Object> appPayRequest;
    private String prepayId;
    private String qrCode;
    private String Sign;
    private String signType;
}
