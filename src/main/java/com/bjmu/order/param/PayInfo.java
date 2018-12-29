package com.bjmu.order.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "保存支付者信息接口参数对象",description = "保存支付者信息接口参数对象")
public class PayInfo {
    private static final long serialVersionUID = -7021613361861360789L;
    @ApiModelProperty(value = "订单号",name = "merOrderId",required = true)
    private String merOrderId;
    @ApiModelProperty(value = "支付用户id",name = "payUserId",required = true)
    private String payUserId;

}
