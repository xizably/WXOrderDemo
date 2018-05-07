package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.OrderDetail;
import cn.mongode.wxorder.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    
    @Autowired
    private OrderServiceImpl orderService;
    
    @Test
    public void create() {
        
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("QHT9527");
        orderDTO.setBuyerAddress("CN.JS.WX.XQ.CJ-GJ-YQ");
        orderDTO.setBuyerPhone("12395279527");
        String BUYER_OPENID = "wxoid952700";
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
        String ORDER_ID = "1525424468790689444";
        OrderDTO orderDTO = orderService.findByOrderId(ORDER_ID);
        log.info("【查询单个订单】 result = {}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }
    
    @Test
    public void findOrderList() {
    
    }
    
    @Test
    public void cancel() {
    }
    
    @Test
    public void finish() {
    }
    
    @Test
    public void paid() {
    }
}