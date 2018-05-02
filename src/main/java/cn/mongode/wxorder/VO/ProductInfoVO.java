package cn.mongode.wxorder.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 * @author: Mongo
 * @date: 2018/4/28
 * @description:
 */
@Data
public class ProductInfoVO {
    
    @JsonProperty("id")
    private String productId;
    
    @JsonProperty("name")
    private String productName;
    
    @JsonProperty("price")
    private BigDecimal productPrice;
    
    @JsonProperty("description")
    private String productDescr;
    
    @JsonProperty("icon")
    private String productIcon;
}
