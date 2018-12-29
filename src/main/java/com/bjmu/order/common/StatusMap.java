package com.bjmu.order.common;

import com.bjmu.order.domain.union.StatusBean;
import com.bjmu.order.domain.union.persistence.PerOrder;
import com.bjmu.order.enmu.StatusEnum;

import java.util.*;

public class StatusMap {

    /*
    * 0-代表灰色
    * 1-绿色
    * 2-代表红色
    * */
    public static Map<Integer,List<StatusBean>> statusLoader(PerOrder perOrder){

        Map<Integer,List<StatusBean>> map = new HashMap<>();

        List<StatusBean> list1 = new ArrayList<>(
                Arrays.asList(
                        StatusBean.builder().desc("生成订单").code(1).time(perOrder.getTxnTime()).num(1).remark("订单号："+perOrder.getOrderId()).build(),
                        StatusBean.builder().desc("支付成功").code(0).time(perOrder.getPaySuccessTime()).num(2).remark("支付方式："+perOrder.getPayType()).build(),
                        StatusBean.builder().desc("处理中").code(0).time(perOrder.getNotifySubMerTime()).num(3).remark("缴网费（预计30分支到账）").build(),
                        StatusBean.builder().desc("交易成功").code(0).time(perOrder.getTradeSuccessTime()).num(4).remark("预计今天交易成功").build()
                )
        );
        List<StatusBean> listFail = new ArrayList<>(
                Arrays.asList(
                        StatusBean.builder().desc("生成订单").code(1).time(perOrder.getTxnTime()).num(1).remark("订单号："+perOrder.getOrderId()).build(),
                        StatusBean.builder().desc("支付成功").code(2).time(perOrder.getPaySuccessTime()).num(2).remark("支付方式："+perOrder.getPayType()).build(),
                        StatusBean.builder().desc("处理中").code(0).time(perOrder.getNotifySubMerTime()).num(3).remark("缴网费（预计30分支到账）").build(),
                        StatusBean.builder().desc("交易成功").code(0).time(perOrder.getTradeSuccessTime()).num(4).remark("预计今天交易成功").build()
                )
        );

        List<StatusBean> list2 = new ArrayList<>(
                Arrays.asList(
                        StatusBean.builder().desc("生成订单").code(1).time(perOrder.getTxnTime()).num(1).remark("订单号："+perOrder.getOrderId()).build(),
                        StatusBean.builder().desc("支付成功").code(1).time(perOrder.getPaySuccessTime()).num(2).remark("支付方式："+perOrder.getPayType()).build(),
                        StatusBean.builder().desc("处理中").code(0).time(perOrder.getNotifySubMerTime()).num(3).remark("缴网费（预计30分支到账）").build(),
                        StatusBean.builder().desc("交易成功").code(0).time(perOrder.getTradeSuccessTime()).num(4).remark("预计今天交易成功").build()
                )
        );
        List<StatusBean> list3 = new ArrayList<>(
                Arrays.asList(
                        StatusBean.builder().desc("生成订单").code(1).time(perOrder.getTxnTime()).num(1).remark("订单号："+perOrder.getOrderId()).build(),
                        StatusBean.builder().desc("支付成功").code(1).time(perOrder.getPaySuccessTime()).num(2).remark("支付方式："+perOrder.getPayType()).build(),
                        StatusBean.builder().desc("处理中").code(1).time(perOrder.getNotifySubMerTime()).num(3).remark("缴网费（预计30分支到账）").build(),
                        StatusBean.builder().desc("交易成功").code(0).time(perOrder.getTradeSuccessTime()).num(4).remark("预计今天交易成功").build()
                )
        );
        List<StatusBean> list4 = new ArrayList<>(
                Arrays.asList(
                        StatusBean.builder().desc("生成订单").code(1).time(perOrder.getTxnTime()).num(1).remark("订单号："+perOrder.getOrderId()).build(),
                        StatusBean.builder().desc("支付成功").code(1).time(perOrder.getPaySuccessTime()).num(2).remark("支付方式："+perOrder.getPayType()).build(),
                        StatusBean.builder().desc("处理中").code(1).time(perOrder.getNotifySubMerTime()).num(3).remark("缴网费（预计30分支到账）").build(),
                        StatusBean.builder().desc("交易成功").code(1).time(perOrder.getTradeSuccessTime()).num(4).remark("预计今天交易成功").build()
                )
        );
        List<StatusBean> list4Fail = new ArrayList<>(
                Arrays.asList(
                        StatusBean.builder().desc("生成订单").code(1).time(perOrder.getTxnTime()).num(1).remark("订单号："+perOrder.getOrderId()).build(),
                        StatusBean.builder().desc("支付成功").code(1).time(perOrder.getPaySuccessTime()).num(2).remark("支付方式："+perOrder.getPayType()).build(),
                        StatusBean.builder().desc("处理中").code(1).time(perOrder.getNotifySubMerTime()).num(3).remark("缴网费（预计30分支到账）").build(),
                        StatusBean.builder().desc("交易成功").code(2).time(perOrder.getTradeSuccessTime()).num(4).remark("预计今天交易成功").build()
                )
        );
        map.put(StatusEnum.NEW_ORDER.getCode(),list1);
        map.put(StatusEnum.PAY_FAIL.getCode(),listFail);
        map.put(StatusEnum.PAY_SUCCESS.getCode(),list2);
        map.put(StatusEnum.MID_ACCEPTING.getCode(),list3);
        map.put(StatusEnum.MID_SUCCESS.getCode(),list4);
        map.put(StatusEnum.MID_FAIL.getCode(),list4Fail);
        return map;
    }

}
