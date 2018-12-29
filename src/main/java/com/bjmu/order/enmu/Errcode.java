package com.bjmu.order.enmu;

import com.bjmu.order.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

public enum  Errcode {

    SUCCESS("SUCCESS","成功"),
    INTERNAL_ERROR("INTERNAL_ERROR","内部错误"),
    BAD_REQUEST("BAD_REQUEST","请求报文有错"),
    NO_SERVICE("NO_SERVICE","没有能处理请求msgtype的服务"),
    TIMEOUT("TIMEOUT","处理超时 "),
    NO_ORDER("NO_ORDER","找不到请求的原始订单"),
    OPERATION_NOT_ALLOWED("OPERATION_NOT_ALLOWED","当前不允许此操作"),
    TARGET_FAIL("TARGET_FAIL","支付宝方支付失败，如请求没有成功，或者请求成功，但是没有正确处理。"),
    DUP_ORDER("DUP_ORDER","重复的订单请求"),
    NET_ERROR("NET_ERROR","跟支付包通讯出问题，包括请求发送异常，报文应答不是200，请求被取消，应答超时等。"),
    NO_MERCHANT("NO_MERCHANT","找不到请求指定的商户"),
    ORDER_PROCESSING("ORDER_PROCESSING","订单正在处理中，不允许并发操作。"),
    INACTIVE_MERCHANT("INACTIVE_MERCHANT","商户被置为inactive状态"),
    ABNORMAL_REQUEST_TIME("ABNORMAL_REQUEST_TIME","请求时间异常"),
    TXN_DISCARDED("TXN_DISCARDED","请求开始处理时间延迟过大，交易被丢弃。"),
    BAD_SIGN("BAD_SIGN","签名错误"),
    INVALID_MSGSRC("INVALID_MSGSRC","商户来源错误"),
    INVALID_ORDER("INVALID_ORDER","订单信息异常"),
    NO_CROSS_DAY_TRADING("NO_CROSS_DAY_TRADING","不允许跨日交易"),
    DENIED_IP("DENIED_IP","不允许此IP交易"),
    INVLID_MERCHANT_CONFIG("INVLID_MERCHANT_CONFIG","错误的商户配置"),
    INVALID_RESPONSE("INVALID_RESPONSE","无效的应答报文");

    @Getter
    @Setter
    public String code;
    @Getter
    @Setter
    private String desc;

    private Errcode(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public final static Errcode newInstance(String code) {
        Errcode[] s_ = Errcode.values();
        if (StringUtils.isNotEmpty(s_)) {
            for (Errcode s_s : s_) {
                if (s_s.getCode().equals(code)) {
                    return s_s;
                }
            }
        }
        return null;
    }




}
