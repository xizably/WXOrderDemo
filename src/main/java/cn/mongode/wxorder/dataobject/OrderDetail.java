package cn.mongode.wxorder.dataobject;

import cn.mongode.wxorder.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情DAO:data access object(数据访问对象)
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 包含商品信息，关联订单主体
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
    
    /* 创建时间.查询时序列化为Long */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    
    /* 更新时间.查询时序列化为Long */
    @JsonSerialize(using = Date2LongSerializer.class)
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
