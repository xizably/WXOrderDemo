package cn.mongode.wxorder.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品VO(ViewObject-表现层对象)
 * @author: Mongo
 * @date: 2018/4/28
 * @description: 包含：商品类目名称、商品类目类型、ProductInfoVO(商品信息VO)列表
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
