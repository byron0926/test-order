package com.bjmu.order.param;

import lombok.Data;

@Data
public class UnionPay {

    private String version;
    private String encoding;
    private String bizType;
    private String txnTime;
    private String backUrl;
    private String currencyCode;
    private String txnAmt;
    private String txnType;
    private String txnSubType;
    private String accessType;
    private String signature;
    private String signMethod;
    private String channelType;
    private String merId;
    private String orderId;
    private String orderDesc;
    private String subMerId;
    private String subMerAbbr;
    private String subMerName;
    private String instalTransInfo;
    private String frontUrl;
    private String accNo;
    private String certId;
    private String payCardType;

}