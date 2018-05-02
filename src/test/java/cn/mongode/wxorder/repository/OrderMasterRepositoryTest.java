package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    
    private final String OPENID = "wxoid789789";
    
    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster("om199qwe",
                "WJY", "12345678999", "CN.JS.WX.XQ.CJGJHY",
                "wxoid789789", new BigDecimal(365.5), 1, 1);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request = new PageRequest(0, 3);
        Page<OrderMaster> orderMaster = orderMasterRepository.findByBuyerOpenid("wxoid789789", request);
        System.out.println(orderMaster.getTotalElements());
        Assert.assertNotEquals(0, orderMaster.getTotalElements());
    }
}