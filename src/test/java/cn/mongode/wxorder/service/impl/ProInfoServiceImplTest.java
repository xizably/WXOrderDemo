package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProInfoServiceImplTest {
    
    @Autowired
    private ProInfoServiceImpl proInfoService;
    
    @Test
    public void findByInfoId() {
    
        ProductInfo productInfo = proInfoService.findByInfoId("223a456b789e");
        Assert.assertEquals("PN03", productInfo.getProductName());
    }
    
    @Test
    public void findUpAll() {
    
        List<ProductInfo> lst = proInfoService.findUpAll();
        Assert.assertNotEquals(0, lst.size());
    }
    
    @Test
    public void findAll() {
    
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> page = proInfoService.findAll(request);
        Assert.assertNotEquals(0, page.getTotalElements());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$: " + page.getTotalElements());
    }
    
    @Test
    public void save() {
        ProductInfo productInfo = proInfoService.findByInfoId("223a456b789e");
        productInfo.setProductId("223a456b789g");
        productInfo.setProductName("PN09");
        productInfo.setProductStock(99);
        productInfo.setProductDescr("PNT09Descr.");
        productInfo.setCategoryType(66);
        
        Assert.assertNotNull(proInfoService.save(productInfo));
    }
}