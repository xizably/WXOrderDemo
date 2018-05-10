package cn.mongode.wxorder.dto;

import cn.mongode.wxorder.dataobject.OrderDetail;
import cn.mongode.wxorder.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 订单DTO - Data Transfer Object（数据传输对象）
 */
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
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
    
    /* 创建时间.查询时序列化为Long */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    
    /* 更新时间.查询时序列化为Long */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    
    /* 订单详情列表. */
    private List<OrderDetail> orderDetailList = new ArrayList<>();
}

