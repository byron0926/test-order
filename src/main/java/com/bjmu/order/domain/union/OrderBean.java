package com.bjmu.order.domain.union;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBean {


    private String payType;//支付方式
    private String orderDesc;//缴费说明
    private String userId;//缴费账号
    private String txnAmt;//缴费金额
    private String bizType;//产品类型
    private String tradeObject;//交易对象
    private Integer status;

    private List<StatusBean> bean;

    private String orderId;//订单号
    private String queryId;//查询流水号
    private String subMerOrderId;//商家订单号



}
