package com.bjmu.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Goods {

    public Goods(String goodsId, String goodsName, Long quantity, Long price, String goodsCategory, String body) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.price = price;
        this.goodsCategory = goodsCategory;
        this.body = body;
    }

    private String goodsId;
    private String goodsName;
    private Long quantity;
    private Long price;
    private String goodsCategory;
    private String body;
}
