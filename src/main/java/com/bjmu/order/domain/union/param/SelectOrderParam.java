package com.bjmu.order.domain.union.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class SelectOrderParam {

    @ApiModelProperty(value = "订单创建人，默认是当前用户,如果orderId有值，则该字段不需要传值",name = "createrId",required = false)
    private String createrId;
    @ApiModelProperty(value = "订单号，传入订单号则 查询结果是该订单号得具体订单信息",name = "orderId",required = false)
    private String orderId;
    @ApiModelProperty(value = "是否查询所有未完成订单标志flag==1",name = "flag",required = false)
    private int flag;
    @ApiModelProperty(value = "是否已接收到异步回调通知",name = "isNotify",required = false)
    private Integer isNotify;

    @ApiModelProperty(value = "付款人id，用于查询付款记录",name = "payerId",required = false)
    private String payerId;
    @ApiModelProperty(value = "订单归属人id,可以不传",name = "belongerId",required = false)
    private String belongerId;
    @ApiModelProperty(value = "用户id",name = "userId",required = false)
    private String userId;



}
