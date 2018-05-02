package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.OrderDetail;
import cn.mongode.wxorder.dataobject.OrderMaster;
import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.dto.CartDTO;
import cn.mongode.wxorder.dto.OrderDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    
    @Autowired
    private ProInfoService proInfoService;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    
    @Override
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
            orderAmount = orderDetail.getProductPrice()
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
        return null;
    }
    
    @Override
    public Page<OrderDTO> findOrderList(String buyerOpenid, Pageable pageable) {
        return null;
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
