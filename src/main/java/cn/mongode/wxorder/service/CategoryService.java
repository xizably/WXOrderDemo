package cn.mongode.wxorder.service;

import cn.mongode.wxorder.dataobject.ProductCategory;

import java.util.List;

/**
 * 商品类目服务
 */
public interface CategoryService {

    ProductCategory findById(Integer categoryId);
    
    List<ProductCategory> findAll();
    
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    
    ProductCategory save(ProductCategory productCategory);
}
