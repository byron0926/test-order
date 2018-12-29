package com.bjmu.order.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class POrderQuery {

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
    private OrderQuery reqData;
    private String reqDataHex;

}
