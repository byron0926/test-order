package com.bjmu.order.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayFlow {

    private String payUserId;
    private String toUserId;
    private Long amount;
    private String orderId;
    private String goodsId;
    private String crtTime;
    private String updTime;
}
