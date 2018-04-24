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
    
    @Autowired
    private ProductCategoryRepository repository;
    
    @Autowired
    public CategoryServiceImpl(ProductCategoryRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public ProductCategory findById(Integer categoryId) {
        if (repository.findById(categoryId).isPresent()) {
            return repository.findById(categoryId).get();
        }
        return null;
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
