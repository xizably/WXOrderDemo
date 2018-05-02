package cn.mongode.wxorder.dataobject;

import cn.mongode.wxorder.enums.OrderStatusEnum;
import cn.mongode.wxorder.enums.PayStatusEnum;
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
public class OrderMaster {
    
    /* 订单id. */
    @Id
    private String orderId;
    
    /* 买家名字. */
    private String buyerName;
    
    /* 买家手机号. */
    private String buyerPhone;
    
    /* 买家地址. */
    private String buyerAddress;
    
    /* 买家微信Openid. */
    private String buyerOpenid;
    
    /* 订单总金额. */
    private BigDecimal orderAmount;
    
    /* 订单状态, 默认新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    
    /* 支付状态, 默认为0, 未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    
    /* 创建时间. */
    private Date createTime;
    
    /* 更新时间. */
    private Date updateTime;
    
    /* 订单详情列表. */
//    不会到数据库查找订单详情列表（新建类-OrderDTO）.
//    @Transient
//    private List<OrderDetail> orderDetailList;
    
    public OrderMaster() {
    }
    
    public OrderMaster(String orderId, String buyerName, String buyerPhone, String buyerAddress,
                       String buyerOpenid, BigDecimal orderAmount, Integer orderStatus, Integer payStatus) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.payStatus = payStatus;
    }
}
