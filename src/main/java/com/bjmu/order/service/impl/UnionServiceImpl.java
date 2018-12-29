package com.bjmu.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjmu.order.common.ResponseResult;
import com.bjmu.order.common.StatusMap;
import com.bjmu.order.domain.union.BackBean;
import com.bjmu.order.domain.union.OrderBean;
import com.bjmu.order.domain.union.param.AppReqParam;
import com.bjmu.order.domain.union.persistence.PerOrder;
import com.bjmu.order.enmu.StatusEnum;
import com.bjmu.order.mapper.UnionOrderMapper;
import com.bjmu.order.service.UnionService;
import com.bjmu.order.unionpay.acp.demo.DemoBase;
import com.bjmu.order.unionpay.acp.sdk.AcpService;
import com.bjmu.order.unionpay.acp.sdk.SDKConfig;
import com.bjmu.order.util.HttpClient;
import com.bjmu.order.util.TempUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service
@Slf4j
public class UnionServiceImpl implements UnionService {


    @Autowired
    private UnionOrderMapper mapper;

    @Value("${notify_sub_mer_url}")
    private String notify_sub_mer_url;
    @Value("${get_sub_mer_order_id}")
    private String get_sub_mer_order_id;

    @Override
    public Object sendPost(AppReqParam param) {
        log.info("打印app请求参数：{}", param);
        try {
            param.setOrderDesc(URLDecoder.decode(param.getOrderDesc(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //加载classpath下的acp_sdk.properties文件内容
        SDKConfig.getConfig().loadPropertiesFromSrc();
        Map<String, String> contentData = new HashMap<String, String>();

        String orderId = TempUtil.getFormatTimeWithNull();
        String txnTime = TempUtil.getFormatTime();
        log.info("订单申请时间打印=" + txnTime);

        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        contentData.put("version", DemoBase.version);            //版本号 全渠道默认值
        contentData.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
        contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
        contentData.put("txnType", "01");                        //交易类型 01:消费
        contentData.put("txnSubType", "01");                    //交易子类 01：消费
        contentData.put("bizType", "000201");                    //填写000201
        contentData.put("channelType", "08");                    //渠道类型 08手机

        /***商户接入参数***/
        contentData.put("merId", "777290058110048");                        //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        contentData.put("accessType", "0");                        //接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
        contentData.put("orderId", orderId);                    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        contentData.put("txnTime", txnTime);                    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        contentData.put("accType", "01");                        //账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
        contentData.put("txnAmt", param.getTxnAmt());                        //交易金额 单位为分，不能带小数点
        contentData.put("currencyCode", "156");                 //境内商户固定 156 人民币
        contentData.put("backUrl", DemoBase.backUrl);

        /*
         * 插入新建订单
         * */
        PerOrder order = PerOrder
                .builder()
                .bizType("000201")
                .currencyCode("156")
                .orderId(orderId)
                .payTimeOut(param.getPayTimeout())
                .txnSubType(param.getTxnSubType())
                .txnTime(txnTime)
                .txnType("01")
                .orderDesc(param.getOrderDesc())
                .subMerAbbr(param.getSubMerAbbr())
                .subMerId("777290058110048")
                .subMerName(param.getSubMerName())
                .txnAmt(Long.valueOf(param.getTxnAmt()))
                .userId(param.getUserId())
                .userName(param.getUserName())
                .payCount(param.getPayCount())
                .build();
        log.info("要插入得订单内容->{}", order);
        mapper.insertOrder(order);

        /**对请求参数进行签名并发送http post请求，接收同步应答报文**/
        Map<String, String> reqData = AcpService.sign(contentData, DemoBase.encoding);             //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();                                 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        Map<String, String> rspData = AcpService.post(reqData, requestAppUrl, DemoBase.encoding);
        return ResponseResult.builder().code(0).msg("响应成功").resData(rspData).build();
    }

    @Override
    public ResponseResult querySingleOrder(String orderId) {

        PerOrder perOrder = mapper.queryOrdersByOrderId(orderId);
        if (perOrder == null) {
            return ResponseResult.builder().msg("订单号不存在").code(-9999).build();
        }
        if (perOrder.getIsNotify() == 1) {
            return ResponseResult
                    .builder()
                    .code(0)
                    .msg("订单详情查询成功")
                    .resData(OrderBean.builder()
                            .payType(perOrder.getPayType())
                            .orderDesc(perOrder.getOrderDesc())
                            .userId(perOrder.getUserId())
                            .txnAmt(String.valueOf(perOrder.getTxnAmt()))
                            .tradeObject("缴网费")
                            .bizType("缴费-生活缴费-网费")
                            .bean(StatusMap.statusLoader(perOrder).get(perOrder.getStatus()))
                            .orderId(perOrder.getOrderId())
                            .queryId(perOrder.getQueryId())
                            .status(perOrder.getStatus())
                            .build())
                    .build();
        }
        SDKConfig.getConfig().loadPropertiesFromSrc();
        Map<String, String> data = new HashMap<String, String>();

        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        data.put("version", DemoBase.version);                 //版本号
        data.put("encoding", DemoBase.encoding);          //字符集编码 可以使用UTF-8,GBK两种方式
        data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
        data.put("txnType", "00");                             //交易类型 00-默认
        data.put("txnSubType", "00");                          //交易子类型  默认00
        data.put("bizType", "000201");                         //业务类型

        /***商户接入参数***/
        data.put("merId", "777290058110048");                               //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("accessType", "0");                           //接入类型，商户接入固定填0，不需修改

        /***要调通交易以下字段必须修改***/
        data.put("orderId", orderId);                            //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
        data.put("txnTime", perOrder.getTxnTime());                            //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/

        Map<String, String> reqData = AcpService.sign(data, DemoBase.encoding);            //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String url = SDKConfig.getConfig().getSingleQueryUrl();                                //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
        Map<String, String> rspData = AcpService.post(reqData, url, DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        log.info("rspData=" + rspData);
        //如果正确返回结果则把queryId更新到数据库
        log.info("respCode=" + rspData.get("respCode"));
        log.info("queryId=" + rspData.get("queryId"));
        log.info("payType=" + rspData.get("payType"));

        if ("00".equals(rspData.get("respCode"))) {
            if ("00".equals(rspData.get("origRespCode"))) {
                mapper.updateStatusByOrderId(orderId, rspData.get("queryId"),rspData.get("payType"));
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("orderId",perOrder.getOrderId());
                paramMap.put("payNum",String.valueOf(perOrder.getTxnAmt()/100));
                paramMap.put("userId",perOrder.getUserId());
                try {
                    log.info("======================进入通知商家充值业务================");
                    HttpClient.newInstance().sendHttpPost(notify_sub_mer_url,JSON.toJSONString(paramMap),null,null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                perOrder.setStatus(StatusEnum.PAY_SUCCESS.getCode());
            }
            if(perOrder.getSubMerOrderId()==null){
                Map<String,String> pm = new HashMap<>();
                pm.put("orderId",perOrder.getOrderId());
                pm.put("userId",perOrder.getUserId());

                try {
                    log.info("======================进入获取商家充值订单号================");
                    HttpClient.ResponseResult responseResult = HttpClient.newInstance().sendHttpPost(get_sub_mer_order_id,JSON.toJSONString(pm),null,null);
                    String p = responseResult.getContent();
                    JSONObject jb = JSON.parseObject(p);
                    if("0".equals(jb.getString("code"))){
                        String subMerOrderId = jb.getString("resData");
                        log.info("subMerOrderId="+subMerOrderId);
                        perOrder.setSubMerOrderId(subMerOrderId);
                        if(subMerOrderId!=null){
                            mapper.updateSubMerOrderId(perOrder.getOrderId(),subMerOrderId);
                            log.info("更新商家订单号成功");
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            perOrder.setPayType(rspData.get("payType"));
            return ResponseResult.builder().code(0).msg(rspData.get("origRespMsg")).resData(OrderBean.builder()
                    .payType(rspData.get("payType"))
                    .orderDesc(perOrder.getOrderDesc())
                    .userId(perOrder.getUserId())
                    .txnAmt(String.valueOf(perOrder.getTxnAmt()))
                    .tradeObject("缴网费")
                    .bizType("缴费-生活缴费-网费")
                    .bean(StatusMap.statusLoader(perOrder).get(perOrder.getStatus()))
                    .orderId(perOrder.getOrderId())
                    .queryId(rspData.get("queryId"))
                    .status(perOrder.getStatus())
                    .subMerOrderId(perOrder.getSubMerOrderId())
                    .build())
                    .build();
        }
        return ResponseResult.builder().code(-9999).msg("未查出订单状态").build();
    }

    @Override
    public Object queryOrdersByUserId(String userId, int flag) {
        List<PerOrder> perOrders = mapper.queryOrdersByUserId(userId, flag);
        return ResponseResult.builder().code(0).msg("订单列表查询成功").resData(perOrders).build();
    }

    @Override
    public Object asyBack(BackBean backBean) {
        return null;
    }


}


