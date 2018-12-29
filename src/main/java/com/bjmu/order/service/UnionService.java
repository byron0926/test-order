package com.bjmu.order.service;

import com.bjmu.order.common.ResponseResult;
import com.bjmu.order.domain.union.BackBean;
import com.bjmu.order.domain.union.param.AppReqParam;


public interface UnionService {
    /*
    * 下单接口
    * */
    Object sendPost(AppReqParam param);
    /*
     * 根据具体订单号查询指定订单详情
     * */
    ResponseResult querySingleOrder(String orderId);
    /*
    * 查询用户下所有订单，flag=1只查询未完成订单
    * */
    Object queryOrdersByUserId(String userId,int flag);

    Object asyBack(BackBean backBean);
}
