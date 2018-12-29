package com.bjmu.order.enmu;

import java.util.HashMap;
import java.util.Map;

public class StatusMap {

    /*UNKNOWN("UNKNOWN","不明确的交易状态"),
     TRADE_CLOSED("TRADE_CLOSED","在指定时间段内未支付时关闭的交易；在交易完成全额退款成功时关闭的交易；支付失败的交易。"),
     WAIT_BUYER_PAY("WAIT_BUYER_PAY","交易创建，等待买家付款。"),
     TRADE_SUCCESS("TRADE_SUCCESS","支付成功"),
     TRADE_REFUND("TRADE_REFUND","订单转入退货流程");*/
    public static Map<String,Integer> getStatus(){
        Map map = new HashMap();
        map.put("UNKNOWN",1);
        map.put("TRADE_CLOSED",21);
        map.put("WAIT_BUYER_PAY",1);
        map.put("TRADE_SUCCESS",3);
        return map;
    }


}
