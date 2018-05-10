package cn.mongode.wxorder.service;

import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品信息服务
 * @author: Mongo
 * @date: 2018/4/24
 * @description: 包含：查询单个商品、查询在架商品、查询所有商品、保存、加库存、减库存
 */
@Service
public interface ProInfoService {
    
    ProductInfo findByInfoId(String infoId);
    
    /* 查询所有在架商品 */
    List<ProductInfo> findUpAll();
    
    Page<ProductInfo> findAll(Pageable pageable);
    
    ProductInfo save(ProductInfo productInfo);
    
    /* 加库存 */
    void increaseStock(List<CartDTO> cartDTOList);
    
    /* 减库存 */
    void decreaseStock(List<CartDTO> cartDTOList);
    
}
