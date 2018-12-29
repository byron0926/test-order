package com.bjmu.order.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
* 历史订单查询接口参数
* 用户信息userId 从token里面取
* */
@Data
//@ApiModel(value = "历史订单查询接口参数对象",description = "历史订单查询接口参数对象")
public class OrderQuery {
    private static final long serialVersionUID = -7021613361861360789L;
//    @ApiModelProperty(value = "查询标志（0-代表查询所有订单/1-代表查询所有未完成订单）",name = "flag",required = true)
    private String flag;
}
