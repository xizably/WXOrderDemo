package cn.mongode.wxorder.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品信息DAO:data access object(数据访问对象)
 * @author: Mongo
 * @date: 2018/4/24
 * @description: 商品具体信息：名称、价格、库存、状态 - 关联商品类目
 */
@Entity
@Data
public class ProductInfo {
    
    @Id
    private String productId;
    
    /* 名称. */
    private String productName;
    
    /* 价格. */
    private BigDecimal productPrice;
    
    /* 库存. */
    private Integer productStock;
    
    /* 描述. */
    private String productDescr;
    
    /* 小图. */
    private String productIcon;
    
    /* 状态 : 0-正常, 1-下架. */
    private Integer productStatus;
    
    /* 类目编号. */
    private Integer categoryType;
    
    public ProductInfo() {
    }
    
    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescr, String productIcon, Integer productStatus, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescr = productDescr;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }
}
