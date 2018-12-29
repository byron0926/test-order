package com.bjmu.order.domain.union;

import lombok.Data;

@Data
public class UnionOrder {

    private String bizType;
    private String txnTime;
    private String txnAmt;
    private String txnType;
    private String txnSubType;
    private String accessType;
    private String channelType;
    private String merId;
    private String orderId;
    private String orderDesc;
    private String subMerId;
    private String subMerAbbr;
    private String subMerName;
    private String instalTransInfo;
    private String accNo;
    private String payCardType;
    private String reserved;
    private String issInsCode;
    private String accSplitData;
    private String riskRateInfo;
    private String ctrlRule;
    private String defaultPayType;
    private String reqReserved;
    private String customerInfo;
    private String payTimeout;

    /*==========================*/

    private int status;
    private String userId;

}
