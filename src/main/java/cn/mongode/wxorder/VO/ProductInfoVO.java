package cn.mongode.wxorder.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情VO(ViewObject-表现层对象)
 * @author: Mongo
 * @date: 2018/4/28
 * @description: 商品的简易信息 - 包含：商品的id、名称、价格、描述、小图地址
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
