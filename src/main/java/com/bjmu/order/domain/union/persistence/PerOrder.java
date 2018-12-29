package com.bjmu.order.domain.union.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 持久化订单
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    private String subMerId;//子商户号
    private String subMerAbbr;//子商户简称
    private String subMerName;//子商户名称
    private Long txnAmt;
    private String txnSubType;
    private String orderDesc;
    private String orderId;
    private String txnTime;
    private String payTimeOut;
    private String currencyCode;//交易币种
    private String txnType;
    private String bizType;
    private Integer status;
    private String payerId;
    private String createrId;
    private String belongerId;
    private Integer isNotify;
    private int flag;
    private String userId;
    private String statusName;
    private String paySuccessTime;
    private String notifySubMerTime;
    private String tradeSuccessTime;
    private String payType;
    private String userName;
    private String payCount;//支付明细
    private String queryId;
    private String subMerOrderId;
}
