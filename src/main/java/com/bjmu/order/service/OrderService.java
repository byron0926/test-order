package com.bjmu.order.service;

import com.bjmu.order.domain.OrderQueryParam;
import com.bjmu.order.domain.ReqOrder;
import com.bjmu.order.param.InitOrder;
import com.bjmu.order.param.PayInfo;


public interface OrderService {


   Object createOrder(InitOrder order);

   String post(String param);

   Object queryOrder(String merOrderId);

   Object closeOrder(OrderQueryParam order);

   Object queryHisOrdersByUserId(String userId, String flag);

   Object save(String card);

    Object queryCardByUserId();

    Object queryPayRecord();

    Object savePayRecord(PayInfo info);
}
