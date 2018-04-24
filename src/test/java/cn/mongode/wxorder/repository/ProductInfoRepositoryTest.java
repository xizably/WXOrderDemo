package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.service.InfoService;
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
public class ProductInfoRepositoryTest {
    
    @Autowired
    private ProductInfoRepository productInfoRepository;
    
    @Autowired
    private InfoService infoService;
    
    @Test
    public void save() throws Exception{
        ProductInfo productInfo = new ProductInfo(
                "323a456b789f",
                "PN07", new BigDecimal(236), 63,"PNT07Descr.",
                "https://cdn.pixabay.com/photo/2018/01/21/01/46/architecture-3095716_960_720.jpg",
                0, 35);
        ProductInfo result = productInfoRepository.save(productInfo);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void findByProductStatus() throws Exception{
        List<ProductInfo> list = productInfoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0, list.size());
    }
    
    
    
}