package com.bjmu.order.domain.union;

import lombok.Data;

@Data
public class BackBean {

    private String queryId;
    private String currencyCode;
    private String traceTime;
    private String settleCurrencyCode;
    private String settleAmt;
    private String settleDate;
    private String traceNo;
    private String respCode;
    private String respMsg;
    private String exchangeDate;
    private String signPubKeyCert;
    private String exchangeRate;
    private String accNo;
    private String payType;
    private String payCardNo;
    private String payCardType;
    private String payCardIssueName;
    /*以下是需要返回得字段*/
    private String version;
    private String bindId;
    private String encoding;
    private String bizType;
    private String txnTime;
    private String txnAmt;
    private String txnType;
    private String txnSubType;
    private String accessType;
    private String reqReserved;
    private String merId;
    private String orderId;


}
