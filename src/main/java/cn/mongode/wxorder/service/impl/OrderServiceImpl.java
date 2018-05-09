package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.convertor.OrderMasterToDTOConvert;
import cn.mongode.wxorder.dataobject.OrderDetail;
import cn.mongode.wxorder.dataobject.OrderMaster;
import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.dto.CartDTO;
import cn.mongode.wxorder.dto.OrderDTO;
import cn.mongode.wxorder.enums.OrderStatusEnum;
import cn.mongode.wxorder.enums.PayStatusEnum;
import cn.mongode.wxorder.enums.ResultEnum;
import cn.mongode.wxorder.exception.OrderException;
import cn.mongode.wxorder.repository.OrderDetailRepository;
import cn.mongode.wxorder.repository.OrderMasterRepository;
import cn.mongode.wxorder.service.OrderService;
import cn.mongode.wxorder.service.ProInfoService;
import cn.mongode.wxorder.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Mongo
 * @date: 2018/5/2
 * @description:
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final ProInfoService proInfoService;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderMasterRepository orderMasterRepository;
    
    @Autowired
    public OrderServiceImpl(ProInfoService proInfoService,
                            OrderDetailRepository orderDetailRepository,
                            OrderMasterRepository orderMasterRepository) {
        this.proInfoService = proInfoService;
        this.orderDetailRepository = orderDetailRepository;
        this.orderMasterRepository = orderMasterRepository;
    }
    
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        
        // 1.查询商品情况(数量，价格) - 是否存在、库存情况、···
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = proInfoService.findByInfoId(orderDetail.getProductId());
            if (productInfo == null) {
                throw new OrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            
            // 2.计算总价 - 价格要从数据库取!
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            
            // 订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        
        // 3.写入订单数据库(orderMaster和orderDetail)
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);
        
        // 4.扣除库存 - 判断库存是否足够
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        proInfoService.decreaseStock(cartDTOList);
    
        return orderDTO;
    }
    
    @Override
    public OrderDTO findByOrderId(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).isPresent()
                ? orderMasterRepository.findById(orderId).get() : null;
        if (orderMaster == null) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
    
    @Override
    public Page<OrderDTO> findOrderList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMasterToDTOConvert.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
    
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】 订单状态异常, orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        
        // 修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateStatus = orderMasterRepository.save(orderMaster);
        if (!updateStatus.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode())) {
            log.error("【取消订单】更新失败, orderMaster = {}", orderMaster);
            throw new OrderException(ResultEnum.ORDER_UPDATE_FALSE);
        }
        
        // 返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO = {}", orderDTO);
            throw new OrderException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().
                map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        proInfoService.increaseStock(cartDTOList);
        
        // 如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
            log.info("如果已支付，需要退款");
        }
        
        return orderDTO;
    }
    
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】 订单状态异常, orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.info("【订单支付】订单支付状态异常, orderDTO = {}", orderDTO);
            throw new OrderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 更新订单支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
    
        // TODO 确认是否支付成功
        
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
    
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单完结】 订单状态异常, orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            log.info("【订单完结】订单支付状态异常, orderDTO = {}", orderDTO);
            throw new OrderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 更新订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);
        
        return orderDTO;
    }
}
