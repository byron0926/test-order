package com.bjmu.order.controller;


import com.alibaba.fastjson.JSON;
import com.bjmu.order.common.ResponseResult;
import com.bjmu.order.domain.union.BackBean;
import com.bjmu.order.domain.union.param.AppReqParam;
import com.bjmu.order.domain.union.param.ReqBean;
import com.bjmu.order.domain.union.param.SelectOrderParam;
import com.bjmu.order.redis.api.RedisDao;
import com.bjmu.order.service.UnionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@Api(value = "union收付款Controller", tags = {"银联云闪付收付款接口"})
@RestController
@RequestMapping(value = "/bjmu/union/order", method = RequestMethod.POST)
public class BjmuUnionController {


    @Autowired
    private UnionService service;

    @Autowired
    private RedisDao redisDao;

    /*
     * 下单接口
     * */
    @RequestMapping("/getTn")
    @ApiOperation("下单接口")
    public Object getTn(@RequestBody ReqBean<AppReqParam> paramBean) {
        String key = paramBean.getToken();
        log.info("token=" + key);
        String value = redisDao.get(key);
        String userId = JSON.parseObject(value).getString("id");
        log.info("userId=" + userId);
        AppReqParam param = paramBean.getReqData();
        param.setUserId(userId);
        log.info("getTn参数打印->{}", param);
        return service.sendPost(param);
    }

    /*
        异步回调接口
     */
    @ApiOperation(value = "异步回调接口")
    @RequestMapping("/asyBack")
    public String asyBack(String param) {
        log.info("异步接收到得参数->{}", param);
        BackBean backBean = JSON.parseObject(param, BackBean.class);
        ResponseResult rr = service.querySingleOrder(backBean.getOrderId());
        return "200";
        //todo
        /*
         * 收到异步回调后需要主动请求查询接口
         * */

    }

    @RequestMapping("/queryOrdersByOrderId")
    @ApiOperation("订单查询接口")
    public Object queryOrdersByOrderId(@RequestBody ReqBean<SelectOrderParam> queryparam) {
        SelectOrderParam param = queryparam.getReqData();
        log.info("订单查询参数打印->{}", param.getOrderId());
        return service.querySingleOrder(param.getOrderId());
    }

    @RequestMapping("/queryOrdersByUserId")
    @ApiOperation("订单查询接口")
    public Object queryOrdersByUserId(@RequestBody ReqBean<SelectOrderParam> queryparam) {
        String key = queryparam.getToken();
        log.info("token=" + key);
        String value = redisDao.get(key);
        String userId = JSON.parseObject(value).getString("id");
        log.info("userId=" + userId);
        SelectOrderParam param = queryparam.getReqData();
        log.info("订单查询参数打印->{}", param);
        return service.queryOrdersByUserId(userId, param.getFlag());
    }
}
