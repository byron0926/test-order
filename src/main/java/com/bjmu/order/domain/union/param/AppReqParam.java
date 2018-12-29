package com.bjmu.order.domain.union.param;


/*
* app下单请求必传字段和选填字段
*
* */

import com.bjmu.order.anno.NotNull;
import com.bjmu.order.anno.NotZero;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppReqParam{

    @ApiModelProperty(value = "子商户号(商户类型为平台类商户接入时必须上送)",name = "subMerId",required = false)
    private String subMerId;//子商户号
    @ApiModelProperty(value = "二级商户简称(商户类型为平台类商户接入时必须上送)",name = "subMerAbbr",required = false)
    private String subMerAbbr;//子商户号
    @ApiModelProperty(value = "二级商户名称(商户类型为平台类商户接入时必须上送)",name = "subMerName",required = false)
    private String subMerName;//子商户名称
    @NotNull
    @NotZero
    @ApiModelProperty(value = "交易金额(商户类型为平台类商户接入时必须上送)",name = "txnAmt",required = true)
    private String txnAmt;
    @ApiModelProperty(value = "交易子类(01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转支付） 03：分期付款，默认01)",name = "txnSubType",required = false)
    private String txnSubType;
    @ApiModelProperty(value = "订单描述",name = "orderDesc",required = false)
    private String orderDesc;
    @ApiModelProperty(value = "订单超时时间",name = "payTimeOut",required = false)
    private String payTimeout;
    @ApiModelProperty(value = "订单创建者id。用于后续查询字段",name = "createrId",required = false)
    private String createrId;
    @ApiModelProperty(value = "订单付款人id，用于后续查询字段,在app接收到付款成功通知后更新此字段",name = "payerId",required = false)
    private String payerId;
    @ApiModelProperty(value = "订单归属人id，用于后续查询字段",name = "belongerId",required = false)
    private String belongerId;
    @ApiModelProperty(value = "token用户id",name = "userId",required = false)
    private String userId = "admin";
    private String userName;
    private String payCount;

}
