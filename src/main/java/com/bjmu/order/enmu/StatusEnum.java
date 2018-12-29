package com.bjmu.order.enmu;

import com.bjmu.order.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

public enum StatusEnum {

    NEW_ORDER(1,"刚创建"),
//    ACCEPTING(10,"订单正在由支付平台处理，等待返回结果"),
    PAY_FAIL(2,"订单支付失败"),
    PAY_OVERTIME(20,"订单超时失效"),//最终状态
    PAY_CANCEL(21,"取消订单"),//最终状态
    PAY_SUCCESS(3,"订单支付成功"),
    MID_ACCEPTING(30,"订单正在由业务系统处理，等待返回结果"),//这是个中间状态
    MID_FAIL(31,"商户订单处理失败"),//最终状态
//    MID_OVERTIME(310,"商户订单处理超时失效"),//转人工处理
    MID_SUCCESS(32,"商户订单自动处理成功");//最终状态
//    MID_MANNUL_SUCCESS(33,"商户订单人工处理成功"),//最终状态
//    MID_MANNUL_BACK(34,"商户订单人工退款");//最终状态

    /*UNKNOWN("UNKNOWN","不明确的交易状态"),
    TRADE_CLOSED("TRADE_CLOSED","在指定时间段内未支付时关闭的交易；在交易完成全额退款成功时关闭的交易；支付失败的交易。"),
    WAIT_BUYER_PAY("WAIT_BUYER_PAY","交易创建，等待买家付款。"),
    TRADE_SUCCESS("TRADE_SUCCESS","支付成功"),
    TRADE_REFUND("TRADE_REFUND","订单转入退货流程");*/

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String desc;

    StatusEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public final static StatusEnum newInstance(int code) {
        StatusEnum[] s_ = StatusEnum.values();
        if (StringUtils.isNotEmpty(s_)) {
            for (StatusEnum s_s : s_) {
                if (s_s.getCode() == code) {
                    return s_s;
                }
            }
        }
        return null;
    }

}
