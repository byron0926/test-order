package com.bjmu.order.domain;

import lombok.Data;


/**
 * 应用注册实体类
 */

@Data
public class ServiceRegister {

    private String app_name;
    //页面跳转地址
    private String app_return_url;
    //后台通知地址
    private String app_notify_url;
    //第三方应用得ip地址
    private String app_ip;

}
