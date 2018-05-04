package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.ProductCategory;
import cn.mongode.wxorder.repository.ProductCategoryRepository;
import cn.mongode.wxorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 * @author: Mongo
 * @date: 2018/4/24
 * @description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    
    private final ProductCategoryRepository repository;
    
    @Autowired
    public CategoryServiceImpl(ProductCategoryRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public ProductCategory findById(Integer categoryId) {
        return repository.findById(categoryId).isPresent() ? repository.findById(categoryId).get() : null;
    }
    
    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }
    
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }
    
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
