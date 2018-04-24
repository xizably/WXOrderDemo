package cn.mongode.wxorder.service;

import cn.mongode.wxorder.dataobject.ProductInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品信息
 * @author: Mongo
 * @date: 2018/4/24
 * @description:
 */
@Service
public interface InfoService {
    
    ProductInfo findByInfoId(String infoId);
    
    List<ProductInfo> findUpAll();
    
    List<ProductInfo> findByInfoStatus(Integer infoStatus);
    
    ProductInfo save(ProductInfo productInfo);
    
}
