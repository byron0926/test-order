package com.bjmu.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrder {


    private String id;
    private String msgId;//F
    private String msgSrc;//t
    private String msgType;//t
    private String requestTimestamp;//t
    private String merOrderId;//T
    private String srcReserve;//f
    private String mid;//t
    private String tid;//t
    private String instMid;//t
    private List<Goods> goods;
    private String attachedData;//f
    private Date expireTime;//f
    private String goodsTag;//f
    private String goodsTradeNo;//f
    private String orderDesc;//f
    private Long originalAmount;//f
    private String productId;//F
    private Long totalAmount;//t
    private Boolean divisionFlag;//f
    private Long platformAmount;//f
    private List<SubOrders> subOrders;//f
    private String notifyUrl;//f
    private String returnUrl;//f
    private String showUrl;//f
    private String signType;//f
    private String tradeType;//f
    private String merchantUserId;//f
    private String mobile;//f
    private String secureTransaction;//f
    private String limitCreditCard;//false
    private String sign;//t
    //用户信息
    private String userId;
    private String status;
    private String subOrdersId;
    private String goodsId;
    private String crtTime;
    private String updTime;
    private String payUserId;
    private String toUserId;








}
