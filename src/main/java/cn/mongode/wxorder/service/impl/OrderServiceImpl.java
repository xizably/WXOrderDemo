package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.convertor.OrderMasterToDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        
        // 4.扣除库存 - 判断库存是否足够
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        proInfoService.decreaseStock(cartDTOList);
    
        BeanUtils.copyProperties(orderMaster, orderDTO);
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
            throw new OrderException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
    
    @Override
    public Page<OrderDTO> findOrderList(String buyerOpenid, Pageable pageable) {
        PageRequest request = new PageRequest(0, 3);
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenid, request);
        
        
        Page<OrderDTO> orderDTOPage = OrderMasterToDTO.convert(orderMasters.getContent());
        
        return orderDTOS;
    }
    
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }
    
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }
    
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
