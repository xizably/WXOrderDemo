package cn.mongode.wxorder.service;

import cn.mongode.wxorder.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
