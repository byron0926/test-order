package com.bjmu.order.enmu;


import com.bjmu.order.util.StringUtils;

public enum SystemCodeEnum {
    ERROR(400,"request error"),
    SUCCESSFUL(1,"response successfully"),
    SYSTEM_ERROR(-9999,"系统错误"),
    HTTP_NO_RESPONSE(-9999,"http no response"),
    FAIL(-9999,"转账失败"),
    DUPLICATE_ERROR(-9999,"请不要重复插入数据"),
    VALIDATION_ILLEGAL(-9999,"验证码错误"),
    PARTNER_ILLEGAL(-9999,"非法合作商户ID"),
    SMS_ERROR(-9999,"短信发送失败"),
    ILLEGAL_REQUEST(-9999,"请求不合法"),
    ILLEGAL_TOKEN(-7777,"非法访问令牌token"),
    PARAM_IS_NOT_NULL(-9999,"参数不能为空"),
    TRADE_ORDER_IS_NOT_EXIST(-9999,"交易订单不存在"),
    SIGN_ERROR(-9999,"签名错误"),
    LOCK_TIMEOUT(-9999,"lock time out");

    private int code;
    private String desc;

    private SystemCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public final static SystemCodeEnum newInstance(int code) {
        SystemCodeEnum[] s_ = SystemCodeEnum.values();
        if (StringUtils.isNotEmpty(s_)) {
            for (SystemCodeEnum s_s : s_) {
                if (s_s.getCode() == code) {
                    return s_s;
                }
            }
        }
        return null;
    }
}
