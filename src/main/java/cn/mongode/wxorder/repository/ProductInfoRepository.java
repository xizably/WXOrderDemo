package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品信息Repository
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 基本方法继承自JpaRepository, 添加的方法名要按照规则
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    
    List<ProductInfo> findByProductStatus(Integer productStatus);
    
}
