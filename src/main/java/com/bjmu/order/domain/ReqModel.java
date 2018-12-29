package com.bjmu.order.domain;

import lombok.Data;

@Data
public class ReqModel {

    private String version;
    private String appId;
    private String ucode;
    /*
    * 1:ios
        2:android
        3:wap
        4:web
        5:other
        6:weixin
    *
    * */
    private int clientType;
    private String appTime;
    private ApiData apiData;
    private String sign;


}
