package com.bjmu.order.controller;


import com.bjmu.order.common.ResponseResult;
import com.bjmu.order.domain.OrderQueryParam;
import com.bjmu.order.domain.PayWay;
import com.bjmu.order.domain.ReqOrder;
import com.bjmu.order.param.*;
import com.bjmu.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@CrossOrigin
@Api(value = "收付款Controller", tags = {"收付款接口"})
@RestController
@RequestMapping(value = "/bjmu/order", method = RequestMethod.POST)
public class BjmuController {


    //读取资源配置参数
    @Value("${url}")
    private String APIurl;
    @Value("${mid}")
    private String mid;
    @Value("${tid}")
    private String tid;
    @Value("${instMid}")
    private String instMid;
    @Value("${msgSrc}")
    private String msgSrc;
    @Value("${key}")
    private String md5Key;

    @Value("${msgType_order}")
    private String msgType_order;

    @Value("${msgType_refund}")
    private String msgType_refund;

    @Value("${msgType_secureCancel}")
    private String msgType_secureCancel;

    @Value("${msgType_secureComplete}")
    private String msgType_secureComplete;

    @Value("${msgType_close}")
    private String msgType_close;

    @Value("${msgType_query}")
    private String msgType_query;

    final private static String msgType = "WXPay.jsPay";
    final private static String msgSrcId = "3194";
    final private static String apiUrl_makeOrder = "https://qr-test2.chinaums.com/netpay-portal/webpay/pay.do";
    final private static String notifyUrl = "http://172.27.49.240:8080/publicpay/notifyUrl.do";
    final private static String returnUrl = "http://172.27.49.240:8080/publicpay/returnUrl.do";


    @Autowired
    private OrderService service;

    @ApiOperation(value = "订单初始化创建接口")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public Object payWay(@RequestBody @ApiParam(required = false) Pparam pparam) {
        return service.createOrder(pparam.getReqData());
    }

    /**
     * 异步回调接口
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "异步回调接口")
    @RequestMapping(value = "/asy", method = RequestMethod.POST)
    public String AsyResponse(@RequestBody String param) {
        return service.post(param);
    }

    /**
     * 根据订单号查询订单详情接口
     */
    @ApiOperation(value = "订单状态查询接口")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Object queryOrder(@RequestBody @ApiParam(value = "根据订单号查询指定订单号得订单状态", required = true) String merOrderId) {
        return service.queryOrder(merOrderId);
    }

    @ApiOperation(value = "订单手动关闭接口")
    @RequestMapping(value = "/close")
    public Object closeOrder(@RequestBody @ModelAttribute OrderQueryParam order) {
        return service.closeOrder(order);
    }

    /*
     *查询历史订单
     */
    @ApiOperation(value = "历史订单查询接口", notes = "返回注册订单号等订单初始化信息")
    @RequestMapping("/his")
    public Object queryHisOrdersByUserId(@RequestBody POrderQuery param) {
        log.info("param—>{}", param);
        return service.queryHisOrdersByUserId("test_user_Id", param.getReqData().getFlag());
    }

    /*
     * 支付方式选择接口
     * */
    @ApiOperation(value = "支付方式查询接口")
    @RequestMapping("/payWay")
    public Object selectPayWay() {

        List<PayWay> payWays = new ArrayList<>(
                Arrays.asList(
                        new PayWay(001, "银联支付"),
                        new PayWay(002, "微信支付"),
                        new PayWay(003, "支付宝支付")
                )
        );
        return ResponseResult.builder().code(200).msg("支付方式查询成功").resData(payWays).build();

    }

    /*
     * 银行卡信息保存（用于app绑定银行卡）
     * */
    @ApiOperation(value = "绑定银行卡接口")
    @RequestMapping("/card")
    public Object saveCard(@RequestBody String card) {
        return service.save(card);
    }

    /*
     * 查询银行卡信息
     * */
    @RequestMapping("/queryCard")
    @ApiOperation(value = "查询绑定得银行卡信息")
    public Object queryCardByUserId() {
        return service.queryCardByUserId();
    }

    /*
     * 查询支付记录
     * */
    @ApiOperation(value = "查询支付记录")
    @RequestMapping("/queryPayRecord")
    public Object queryPayRecord() {
        return service.queryPayRecord();
    }

    /*
     * 保存支付者得支付记录
     * */

    @RequestMapping(value = "/savePayRecord")
    @ApiOperation(value = "当支付结果完成时，需要app主动请求后台保存支付者信息")
    public Object savePayRecord(@ModelAttribute PPayInfo info) {
        return service.savePayRecord(info.getReqData());
    }


}
