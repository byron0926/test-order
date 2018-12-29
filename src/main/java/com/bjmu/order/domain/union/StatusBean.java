package com.bjmu.order.domain.union;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusBean {


    private String desc;
    private String time;
    private int code;
    private int num;
    private String remark;

}
