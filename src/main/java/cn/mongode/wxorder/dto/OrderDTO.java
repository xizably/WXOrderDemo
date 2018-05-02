package cn.mongode.wxorder.dto;

import cn.mongode.wxorder.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: Mongo
 * @date: 2018/5/2
 * @description:
 */
@Data
public class OrderDTO {
    
    /* 订单id. */
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
    private Integer orderStatus;
    
    /* 支付状态, 默认为0, 未支付. */
    private Integer payStatus;
    
    /* 创建时间. */
    private Date createTime;
    
    /* 更新时间. */
    private Date updateTime;
    
    /* 订单详情列表. */
    private List<OrderDetail> orderDetailList;
}
