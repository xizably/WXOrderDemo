package cn.mongode.wxorder.service.impl;

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
public class CategoryServiceImplTest {
    
    @Autowired
    private CategoryServiceImpl categoryService;
    
    @Test
    public void findById() throws Exception{
        ProductCategory productCategory = categoryService.findById(3);
        Assert.assertEquals(new Integer(3), productCategory.getCategoryId());
    }
    
    @Test
    public void findAll()throws Exception{
        List<ProductCategory> result = categoryService.findAll();
        Assert.assertNotEquals(0, result.size());
    }
    
    @Test
    public void findByCategoryTypeIn() throws Exception{
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 4));
        Assert.assertNotEquals(0, result.size());
    }
    
    @Test
    @Transactional
    public void save() throws Exception{
        Assert.assertNotNull(categoryService.save(new ProductCategory("灭世榜", 66)));
    }
}