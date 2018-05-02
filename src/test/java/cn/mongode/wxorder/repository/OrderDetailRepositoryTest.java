package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail("od123dfg", "om100zxc",
                "223a456b789g", "PN09", new BigDecimal(189.00), 1,
                "https://cdn.pixabay.com/photo/2018/01/21/01/46/architecture-3095716_960_720.jpg");
        OrderDetail result = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId("om100zxc");
        System.out.println(orderDetails);
        Assert.assertNotEquals(0, orderDetails.size());
    }
}