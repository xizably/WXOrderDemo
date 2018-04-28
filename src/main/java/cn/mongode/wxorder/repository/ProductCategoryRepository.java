package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Mongo
 * @date: 2018/4/23
 * @description: 商品库存仓储
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
