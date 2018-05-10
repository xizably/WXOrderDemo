package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品类目Repository
 * @author: Mongo
 * @date: 2018/4/23
 * @description: 基本方法继承自JpaRepository, 添加的方法名要按照规则
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
