package com.bjmu.order.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {

    private int code;
    private String msg;
    private Object resData;

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
