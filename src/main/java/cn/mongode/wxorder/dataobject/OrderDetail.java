package cn.mongode.wxorder.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Mongo
 * @date: 2018/5/2
 * @description:
 */
@Entity
@Data
public class OrderDetail {
    
    @Id
    private String detailId;
    
    /* 订单id. */
    private String orderId;
    
    /* 商品id. */
    private String productId;
    
    /* 商品名称. */
    private String productName;
    
    /* 商品单价. */
    private BigDecimal productPrice;
    
    /* 商品数量. */
    private Integer productQuantity;
    
    /* 商品小图. */
    private String productIcon;
    
    /* 创建时间. */
    private Date createTime;
    
    /* 更新时间. */
    private Date updateTime;
    
    public OrderDetail() {
    }
    
    public OrderDetail(String detailId, String orderId, String productId, String productName,
                       BigDecimal productPrice, Integer productQuantity, String productIcon) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productIcon = productIcon;
    }
}
