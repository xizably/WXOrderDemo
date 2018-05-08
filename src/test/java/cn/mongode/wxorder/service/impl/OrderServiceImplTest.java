package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.OrderDetail;
import cn.mongode.wxorder.dto.OrderDTO;
import cn.mongode.wxorder.enums.OrderStatusEnum;
import cn.mongode.wxorder.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    
    @Autowired
    private OrderServiceImpl orderService;
    private String BUYER_OPENID = "wxoid789789";
    private String ORDER_ID = "1525422207149507756";
    
    @Test
    public void create() {
        
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("QHT9527");
        orderDTO.setBuyerAddress("CN.JS.WX.XQ.CJ-GJ-YQ");
        orderDTO.setBuyerPhone("12395279527");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("223a456b789g");
        orderDetail.setProductQuantity(4);
        orderDetailList.add(orderDetail);
        
        orderDetail.setProductId("323a456b789f");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);
        
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("Create Order => result = {}.", result);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void findByOrderId() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        log.info("【查询单个订单】 result = {}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }
    
    @Test
    public void findOrderList() {
        PageRequest request = new PageRequest(0, 5);
        Page<OrderDTO> orderDTOPage = orderService.findOrderList(BUYER_OPENID, request);
        log.info("【查询个人订单列表】 result = {}\nTotalElements = {}", orderDTOPage.getContent(), orderDTOPage.getTotalElements());
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }
    
    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        orderDTO = orderService.cancel(orderDTO);
        log.info("Order Cancel Result = {}", orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), orderDTO.getOrderStatus());
    }
    
    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        orderDTO = orderService.paid(orderDTO);
        log.info("Order Paid Result = {}", orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), orderDTO.getPayStatus());
    }
    
    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        orderDTO = orderService.finish(orderDTO);
        log.info("Order Finish Result = {}", orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), orderDTO.getOrderStatus());
    }
}