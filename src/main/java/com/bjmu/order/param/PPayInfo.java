package com.bjmu.order.param;

import lombok.Data;

@Data
public class PPayInfo {


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
    private PayInfo reqData;
    private String reqDataHex;
}
