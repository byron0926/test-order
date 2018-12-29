package com.bjmu.order.exception;

import com.bjmu.order.common.ResponseResult;
import com.bjmu.order.enmu.SystemCodeEnum;
import com.bjmu.order.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalException {


    @ExceptionHandler
    public ResponseResult handle(Exception e) {
        log.error("全局异常捕捉处理");
        log.error(StringUtils.format("异常信息:{0}", e.getMessage()));
        SystemCodeEnum s_ = SystemCodeEnum.SYSTEM_ERROR;

        if (e instanceof MissingServletRequestParameterException) {
            return new ResponseResult(-9999, "参数丢失");
        } else if (e instanceof IllegalOptaionException) {
            IllegalOptaionException i_ = (IllegalOptaionException) e;
            if (StringUtils.isNotEmpty(i_) && StringUtils.isNotEmpty(i_.getS_())) {
                s_ = i_.getS_();
            }
            log.error("异常信息", e);
            return ResponseResult.builder()
                    .code(-9999)
                    .msg(s_.getDesc())
                    .build();
        } else {
            log.error("异常信息", e);
//            return new ResponseResult(-9999, "服务器内部错误", e.getMessage());
            return new ResponseResult(-9999, "服务器内部错误");
        }
    }
}
