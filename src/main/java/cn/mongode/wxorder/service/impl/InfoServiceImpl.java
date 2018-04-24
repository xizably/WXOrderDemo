package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.repository.ProductInfoRepository;
import cn.mongode.wxorder.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Mongo
 * @date: 2018/4/24
 * @description:
 */
public class InfoServiceImpl implements InfoService {
    
    @Autowired
    private ProductInfoRepository repository;
    
    @Override
    public ProductInfo findByInfoId(String infoId) {
        if (repository.findById(infoId).isPresent()) {
            return repository.findById(infoId).get();
        }
        return null;
    }
    
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findAll();
    }
    
    @Override
    public List<ProductInfo> findByInfoStatus(Integer infoStatus) {
        return repository.findByProductStatus(infoStatus);
    }
    
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
