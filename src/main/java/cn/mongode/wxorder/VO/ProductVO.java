package cn.mongode.wxorder.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目）
 * @author: Mongo
 * @date: 2018/4/28
 * @description:
 */
@Data
public class ProductVO {
    
    @JsonProperty("name")
    private String categoryName;
    
    @JsonProperty("type")
    private Integer categoryType;
    
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
