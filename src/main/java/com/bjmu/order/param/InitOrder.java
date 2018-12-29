package com.bjmu.order.param;

import com.bjmu.order.domain.Goods;
import com.bjmu.order.domain.SubOrders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 初始化创建订单参数对象
 */
@Data
@ApiModel(value = "创建订单参数对象",description = "创建订单参数对象")
public class InitOrder {

    private static final long serialVersionUID = -7021613361861360789L;

    @ApiModelProperty(value = "商户号",name = "mid",required = true)
    private String mid;//t
    @ApiModelProperty(value = "终端号",name = "tid",required = true)
    private String tid;//t
    @ApiModelProperty(value = "机构商户号",name = "instMid",required = true)
    private String instMid;//t
    @ApiModelProperty(value = "产品数组",name = "goods",required = false)
    private List<Goods> goods;
    @ApiModelProperty(value = "商户附加数据",name = "attachedData",required = false)
    private String attachedData;//f
    @ApiModelProperty(value = "订单过期时间",name = "expireTime",required = false)
    private Date expireTime;//f
    @ApiModelProperty(value = "商品标记（用于优惠活动）",name = "goodsTag",required = false)
    private String goodsTag;//f
    @ApiModelProperty(value = "订单描述",name = "orderDesc",required = false)
    private String orderDesc;//f
    @ApiModelProperty(value = "订单原始金额",name = "originalAmount",required = true)
    private Long originalAmount;//f
    @ApiModelProperty(value = "支付总金额",name = "totalAmount",required = true)
    private Long totalAmount;//t
    @ApiModelProperty(value = "分账标记",name = "divisionFlag",required = false)
    private Boolean divisionFlag;//f
    @ApiModelProperty(value = "平台商户分账金额(若divisionFlag传true则该字段必传)",name = "platformAmount",required =false )
    private Long platformAmount;//f
    @ApiModelProperty(value = "子商户数组",name = "subOrders",required = false)
    private List<SubOrders> subOrders;//f
    @ApiModelProperty(value = "交易类型，微信必传",name = "tradeType",required = false)
    private String tradeType;//f
    @ApiModelProperty(value = "商户用户号,全民付必传",name = "merchantUserId",required = false)
    private String merchantUserId;//f
    @ApiModelProperty(value = "手机号,全民付必传",name = "mobile",required = false)
    private String mobile;//f
    @ApiModelProperty(value = "表示是否为担保交易",name ="secureTransaction",required = false)
    private String secureTransaction;//f
    @ApiModelProperty(value = "是否限制信用卡支付,默认false",name = "limitCreditCard",required = false)
    private String limitCreditCard;//false

    //用户信息
    @ApiModelProperty(value = "用户id，也是订单发起人",name = "userId",required = false)
    private String userId;
    private String subOrdersId;
    private String goodsId;
    @ApiModelProperty(value = "订单受益人得用户id,默认为当前登录用户既userId",name = "toUserId",required = false)
    private String toUserId;


}
