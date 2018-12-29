package com.bjmu.order.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import sun.misc.Request;

/**
 * app请求公共参数
 */
@Data
@ApiModel
public class Pparam {


    private String sign;
    private String clientMac;
    private String clientType;
    private String version;
    private String clientIp;
    private String magic;
    private String appId;
    private String token;
    private String orgCode;
    private String dateTime;
    private String appVersion;
    private String clientMark;
    private InitOrder reqData;
    private String reqDataHex;


}
