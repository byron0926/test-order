package com.bjmu.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.bjmu.order.common.ResponseResult;
import com.bjmu.order.common.util.NotifyUtil;
import com.bjmu.order.domain.*;
import com.bjmu.order.enmu.Errcode;
import com.bjmu.order.enmu.StatusMap;
import com.bjmu.order.mapper.OrderMapper;
import com.bjmu.order.param.InitOrder;
import com.bjmu.order.param.PayInfo;
import com.bjmu.order.service.OrderService;
import com.bjmu.order.util.TempUtil;
import com.bjmu.order.util.Util;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static com.bjmu.order.util.Util.makeSign;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Resource
    private OrderMapper orderMapper;


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


    final private static String apiUrl_makeOrder = "https://qr-test2.chinaums.com/netpay-portal/webpay/pay.do";
    final private static String notifyUrl = "http://172.16.15.105:9992/bjmu/order/asy";


    /**
     * 入单接口（下单）
     *
     * @param order
     * @return
     */
    @Override
    public Object createOrder(InitOrder order) {

        log.info("app传参order->{}", order);

        ReqOrder reqOrder = new ReqOrder();

        if (order == null || order.getMid() == null) {
            log.info("进入模拟数据。。。");
            reqOrder.setId(UUID.randomUUID().toString());
            reqOrder.setMerOrderId(TempUtil.getFormatTimeWithNull());
            reqOrder.setUserId("test_user_Id");
            reqOrder.setPayUserId("test_user_Id");
            reqOrder.setToUserId("test_user_Id");
            reqOrder.setMsgType("uac.appOrder");
            log.info(reqOrder.getMerOrderId());
            log.info(reqOrder.getUserId());

            orderMapper.initOrder(reqOrder);
//            PayFlow payFlow = PayFlow.builder().payUserId("test_user_Id")
//                    .toUserId("test_user_Id").amount(3l).goodsId(order.getGoodsId())
//                    .orderId(order.getMerOrderId())
//                    .build();
//            log.info("payFlow->{}",payFlow);
//            orderMapper.insertFlow(payFlow);
//            log.info("支付记录插入成功");
            log.info("初始化订单成功...");

            /**
             * 保存订单产品信息到数据库
             */
            JSONObject json = new JSONObject();
            json.put("mid", "898201612345678");
            json.put("tid", "88880001");
            json.put("msgType", "uac.appOrder");
            json.put("msgSrc", "WWW.TEST.COM");
            json.put("instMid", "APPDEFAULT");
            json.put("merOrderId", reqOrder.getMerOrderId());
            json.put("totalAmount", 3);
            json.put("notifyUrl", notifyUrl);
//            json.put("tradeType", order.getTradeType());
            json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Map<String, String> paramsMap = Util.jsonToMap(json);
            paramsMap.put("sign", makeSign(md5Key, paramsMap));
            log.info("paramsMap->{}", paramsMap);

            String strReqJsonStr = JSON.toJSONString(paramsMap);

            log.info("toJsonString->{}", strReqJsonStr);
            String result = this.commonApi(strReqJsonStr, APIurl, "POST");

            log.info("下单完成");
            Map map = new HashMap();
            ResultRegister rr = JSON.parseObject(result, ResultRegister.class);
            if (rr.getRespStr() instanceof String) {
                log.info("rr.getRespStr是String类型");
                String param = (String) rr.getRespStr();
                map = JSON.parseObject(param, Map.class);
            }
            log.info("下单注册结果->{}", rr.getRespStr());
            if (rr != null && Errcode.SUCCESS.getCode().equals(rr.getErrCode())) {
                return ResponseResult.builder().code(0).msg("订单注册完成").resData(map).build();
            }
            return ResponseResult.builder().code(-9999).msg("订单创建失败").resData(map).build();
        }
        return null;
       /* order.setId(UUID.randomUUID().toString());
        //初始化订单状态
        order.setStatus(StatusEnum.NEW_ORDER.getCode());
        //生成订单号
        order.setMerOrderId(this.getFormatTimeWithNull());
        orderMapper.initOrder(order);

        log.info("初始化订单成功...");

        *//**
         * 保存订单产品信息到数据库
         *//*
        JSONObject json = new JSONObject();
        json.put("mid", order.getMid());
        json.put("tid", order.getTid());
        json.put("msgType", order.getMsgType());
        json.put("msgSrc", order.getMsgSrc());
        json.put("instMid", order.getInstMid());
        json.put("merOrderId", order.getMerOrderId());
        json.put("totalAmount", order.getTotalAmount());
        json.put("tradeType", order.getTradeType());
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(md5Key, paramsMap));
        log.info("paramsMap->{}",paramsMap);

        String strReqJsonStr = com.alibaba.fastjson.JSON.toJSONString(paramsMap);
        log.info("toJsonString->{}",strReqJsonStr);
        String result = this.commonApi(strReqJsonStr,APIurl,"POST");
        log.info("下单完成");
        ResultRegister rr = com.alibaba.fastjson.JSON.parseObject(result,ResultRegister.class);
        log.info("下单注册结果->{}",rr);
        return ResponseResult.builder().code(0).msg("订单注册完成").resData(rr).build();*/
    }

    /**
     * 异步回调接口
     *
     * @param param
     * @return
     */
    @Override
    public String post(String param) {
        log.info("异步回调参数->{}", param);

        /*
         * 验签
         * */
        Map<String, String> map = JSON.parseObject(param, Map.class);
        ResponseResult rr = new ResponseResult();
        if (NotifyUtil.checkSign2(md5Key, map)) {
            log.info("验签成功");
            log.info("异步响应得参数->{}", param);
            ResultPay resultPay = JSON.parseObject(param, ResultPay.class);
            if (3 == orderMapper.queryByOrderId(resultPay.getMerOrderId())) {
                rr.setCode(200);
                rr.setMsg("订单已成功付款，不用重复回调");
            } else {
                orderMapper.updateOrder(resultPay.getMerOrderId(), StatusMap.getStatus().get(resultPay.getStatus()));
                rr.setCode(200);
                rr.setMsg("已成功接收异步回调");
            }
        } else {
            log.info("验签失败");
        }
        return JSON.toJSONString(rr);
        //调用本地数据库修改订单状态（在修改之前，查找改订单是否已经处于最终状态）
//        String result = this.commonApi(param,notifyUrl,"POST");
    }

    /**
     * 订单查询接口
     *
     * @param merOrderId
     * @return
     */
    @Override
    public Object queryOrder(String merOrderId) {

        JSONObject json = new JSONObject();
        json.put("msgType", "msgType");
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("msgSrc", "WWW.TEST.COM");
        json.put("mid", "898201612345678");
        json.put("tid", "88880001");
        json.put("merOrderId", "319420181220152408689");
        json.put("instMid", "APPDEFAULT");
        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(md5Key, paramsMap));
        log.info("paramsMap->{}", paramsMap);

        String strReqJsonStr = com.alibaba.fastjson.JSON.toJSONString(paramsMap);
        log.info("toJsonString->{}", strReqJsonStr);
        return this.commonApi(strReqJsonStr, APIurl, "POST");
    }

    /**
     * 关闭订单接口
     *
     * @return
     */
    @Override
    public Object closeOrder(OrderQueryParam order) {

        //更新数据库中订单状态为手动关闭
        //之后通知银联关闭此订单

        JSONObject json = new JSONObject();
        json.put("msgType", order.getMsgType());
        json.put("requestTimestamp", order.getRequestTimestamp());
        json.put("msgSrc", order.getMsgSrc());
        json.put("mid", order.getMid());
        json.put("tid", order.getTid());
        json.put("instMid", order.getInstMid());
        json.put("merOrderId", order.getMerOrderId());
        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(md5Key, paramsMap));
        log.info("paramsMap->{}", paramsMap);

        String strReqJsonStr = JSON.toJSONString(paramsMap);
        log.info("toJsonString->{}", strReqJsonStr);
        return this.commonApi(strReqJsonStr, APIurl, "POST");
    }

    @Override
    public Object queryHisOrdersByUserId(String userId, String flag) {
        List<ReqOrder> list = new ArrayList<>();
        log.info("userId->{}", userId);
        log.info("flag->{}", flag);
        if ("0".equals(flag)) {
            list = orderMapper.queryHisOrdersByUserId(userId);
        } else {
            list = orderMapper.queryHisOrdersByUserIdWithStatus(userId);
        }
        return ResponseResult.builder().code(0).msg("历史订单查询成功").resData(list).build();
    }

    @Override
    public Object save(String card) {
        String userId = "test_user_Id";
        orderMapper.save(card, userId);
        return ResponseResult.builder().code(0).msg("银行卡已记住").build();
    }

    @Override
    public Object queryCardByUserId() {

        //通过token拿到userId
        String userId = "test_user_Id";
        List<Card> list = orderMapper.queryCardByUserId(userId);
        return ResponseResult.builder().code(0).msg("查询银行卡绑定信息成功").resData(list).build();
    }

    /*
     * 查询支付记录
     * */
    @Override
    public Object queryPayRecord() {

        String userId = "test_user_id";
        List<PayFlow> list = orderMapper.queryPayRecord(userId);
        return ResponseResult.builder().code(0).msg("查询支付记录成功").resData(list).build();
    }

    @Override
    public Object savePayRecord(PayInfo info) {
        int res = orderMapper.savePayRecord(info);
        if (res == 1) {
            return ResponseResult.builder().msg("支付人信息保存成功").code(0).build();
        }
        return ResponseResult.builder().msg("支付人信息保存不成功").code(-9999).build();
    }

    private String commonApi(String strReqJsonStr, String APIurl, String method) {
        log.info("strReqJsonStr->{}", strReqJsonStr);
        log.info("apiUrl->{}", APIurl);
        log.info("method->{}", method);
        HttpURLConnection httpURLConnection = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String resultStr = null;
        Map<String, String> resultMap = new HashMap<String, String>();
        if (!StringUtils.isNotBlank(APIurl)) {
            resultMap.put("errCode", "URLFailed");
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }
        try {
            URL url = new URL(APIurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content_Type", "application/json");
            httpURLConnection.setRequestProperty("Accept_Charset", "UTF-8");
            httpURLConnection.setRequestProperty("contentType", "UTF-8");
            //发送POST请求参数
            out = new PrintWriter(httpURLConnection.getOutputStream());
            out.write(strReqJsonStr);
            out.flush();

            //读取响应
            log.info("响应结果msg->{}", httpURLConnection.getResponseMessage());
            log.info("响应结果code->{}", httpURLConnection.getResponseCode());
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = null;
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((tempStr = in.readLine()) != null) {
                    content.append(tempStr);
                }
                log.info("content->{}", content);
                //转换成json对象
                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
                String resultCode = respJson.getString("errCode");
                resultMap.put("errCode", resultCode);
                log.info("errcode->{}", resultCode);
                resultMap.put("respStr", respJson.toString());
                resultStr = JSONObject.fromObject(resultMap).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errCode", "HttpURLException");
            resultMap.put("msg", "调用银商接口出现异常：" + e.toString());
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        } finally {
            if (out != null) {
                out.close();
            }
            httpURLConnection.disconnect();
        }
        System.out.println("resultStr:" + resultStr);
        return resultStr;
    }

}
