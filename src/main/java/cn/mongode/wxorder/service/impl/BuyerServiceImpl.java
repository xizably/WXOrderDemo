package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dto.OrderDTO;
import cn.mongode.wxorder.enums.ResultEnum;
import cn.mongode.wxorder.exception.OrderException;
import cn.mongode.wxorder.service.BuyerService;
import cn.mongode.wxorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 买家服务实现
 * @author: Mongo
 * @date: 2018/5/10
 * @description: 买家服务(判定已存在订单的所属是否正确) - 已存在订单的处理：单个查询、列表查询、订单取消；
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    
    private final OrderService orderService;
    
    @Autowired
    public BuyerServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }
    
    /**
     * 根据订单id查询订单及详情(预先判定openid是否正确有效)
     * @param openid 用户微信openid
     * @param orderId 订单id
     * @return OrderDTO
     * @throws Exception ResultEnum.ORDER_OWNER_ERROR
     */
    private OrderDTO checkOrderOwner(String openid, String orderId) throws Exception {
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        if (orderDTO == null) {
            log.info("【查询订单】订单不存在");
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            log.error("【查询订单】订单的openid不一致. openid = {}, orderDTO = {}", openid, orderDTO);
            throw new OrderException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
    
    /**
     * 根据orderId查询订单
     * @param openid 用户微信openid
     * @param orderId 订单id
     * @return OrderDTO
     * @throws Exception ResultEnum.ORDER_OWNER_ERROR
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) throws Exception {
        return checkOrderOwner(openid, orderId);
    }
    
    /**
     * 根据用户的微信openid获取订单列表 - 控制显示的页数和条数
     * @param openid 用户微信openid
     * @param page 要显示第几页的页数
     * @param size 要显示的数据条数
     * @return OrderDTO列表 - Page<OrderDTO>
     * @throws OrderException ResultEnum.PARAM_ERROR - openid异常
     */
    @Override
    public Page<OrderDTO> findOrderList(String openid, Integer page, Integer size) throws OrderException {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page, size);
        return orderService.findOrderList(openid, request);
    }
    
    /**
     * 根据订单id取消订单
     * @param openid 用户微信openid
     * @param orderId 订单id
     * @throws Exception ResultEnum.ORDER_NOT_EXIST
     */
    @Override
    public void cancelOrder(String openid, String orderId) throws Exception {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】订单不存在, orderId = {}", orderId);
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        orderService.cancel(orderDTO);
    }
    
}
