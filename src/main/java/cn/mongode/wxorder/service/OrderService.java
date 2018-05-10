package cn.mongode.wxorder.service;

import cn.mongode.wxorder.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: Mongo
 * @date: 2018/5/10
 * @description: 订单服务接口 - 订单的处理：订单的创建、单个查询、列表查询、取消、完结、支付；
 */
public interface OrderService {
    
    /* 创建订单. */
    OrderDTO create(OrderDTO orderDTO);
    
    /* 查询单个订单. */
    OrderDTO findByOrderId(String orderId) throws Exception;
    
    /* 根据用户微信openid查询订单列表. */
    Page<OrderDTO> findOrderList(String buyerOpenid, Pageable pageable);
    
    /* 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);
    
    /* 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);
    
    /* 支付订单. */
    OrderDTO paid(OrderDTO orderDTO);
}
