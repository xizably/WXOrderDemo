package cn.mongode.wxorder.service;

import cn.mongode.wxorder.dto.OrderDTO;
import org.springframework.data.domain.Page;

/**
 * 买家服务接口
 * @author: Mongo
 * @date: 2018/5/10
 * @description: 已存在订单的处理：单个订单查询、订单列表查询、订单取消；
 */
public interface BuyerService {
    
    OrderDTO findOrderOne(String openid, String orderId) throws Exception;
    
    Page<OrderDTO> findOrderList(String openid, Integer page, Integer size) throws Exception;
    
    void cancelOrder(String openid, String orderId) throws Exception;
}
