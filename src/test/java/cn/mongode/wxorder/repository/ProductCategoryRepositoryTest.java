package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    
    @Test
    public void findOneTest() {
        if (repository.findById(1).isPresent()) {
            ProductCategory productCategory = repository.findById(1).get();
            System.out.println(productCategory.toString());
        } else {
            System.out.println("查无此类目...");
        }
    }
    
    @Test
//    仅用于测试，测试结束数据不会存在于数据库中.
    @Transactional
    public void addOneTest() {
        ProductCategory productCategory = new ProductCategory("枭雄榜", 3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
        Assert.assertNotEquals(null, result);
    }
    
    @Test
    public void updateOneTest() {
        if (repository.findById(2).isPresent()) {
            ProductCategory productCategory = repository.findById(2).get();
            productCategory.setCategoryType(7);
            repository.save(productCategory);
        } else {
            System.out.println("查无此类目...");
        }
    }
    
    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(2,3,4);
        
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }
}