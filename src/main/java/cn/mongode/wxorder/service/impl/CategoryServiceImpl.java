package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.ProductCategory;
import cn.mongode.wxorder.repository.ProductCategoryRepository;
import cn.mongode.wxorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类目服务
 * @author: Mongo
 * @date: 2018/4/24
 * @description: 包含：单个类目查询、查询所有类目、根据类目类型列表查询类目、保存类目
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    
    private final ProductCategoryRepository repository;
    
    @Autowired
    public CategoryServiceImpl(ProductCategoryRepository repository) {
        this.repository = repository;
    }
    
    /**
     * 根据类目id查询商品类目
     * @param categoryId 商品类目id
     * @return 商品类目
     */
    @Override
    public ProductCategory findById(Integer categoryId) {
        return repository.findById(categoryId).isPresent() ? repository.findById(categoryId).get() : null;
    }
    
    /**
     * 查询所有商品类目
     * @return 商品类目列表
     */
    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }
    
    /**
     * 根据类目类型值列表查询商品类目列表
     * @param categoryTypeList 类目类型值列表
     * @return 商品类目列表
     */
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }
    
    /**
     * 保存商品类目
     * @param productCategory 商品类目
     * @return 保存的商品类目
     */
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
