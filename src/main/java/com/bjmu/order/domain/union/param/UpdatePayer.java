package com.bjmu.order.domain.union.param;

import lombok.Data;

@Data
public class UpdatePayer {

    private String orderId;
    private String payerId;

}
