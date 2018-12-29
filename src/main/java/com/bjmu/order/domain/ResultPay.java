package com.bjmu.order.domain;

import lombok.Data;

@Data
public class ResultPay {

    private String mid;
    private String tid;
    private String instMid;
    private String attachedData;
    private String bankCardNo;
    private String billFunds;
    private String billFundsDesc;
    private String buyerId;
    private String buyerUsername;
    private String buyerPayAmount;
    private String totalAmount;
    private String invoiceAmount;
    private String merOrderId;
    private String payTime;
    private String receiptAmount;
    private String refId;
    private String refundAmount;
    private String seqId;
    private String settleDate;
    private String status;
    private String subBuyerId;
    private String targetOrderId;
    private String targetSys;
    private String sign;


}
