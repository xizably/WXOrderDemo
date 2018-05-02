package cn.mongode.wxorder.dto;

import lombok.Data;

/**
 * 购物车
 * @author: Mongo
 * @date: 2018/5/2
 * @description:
 */
@Data
public class CartDTO {
    
    /* 商品Id. */
    private String productId;
    
    /* 数量. */
    private Integer productQuantity;
    
    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
