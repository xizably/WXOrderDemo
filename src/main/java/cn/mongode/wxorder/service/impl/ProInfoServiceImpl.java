package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.enums.ProductStatusEnum;
import cn.mongode.wxorder.repository.ProductInfoRepository;
import cn.mongode.wxorder.service.ProInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Mongo
 * @date: 2018/4/24
 * @description:
 */
@Service
public class ProInfoServiceImpl implements ProInfoService {
    
    private final ProductInfoRepository repository;
    
    @Autowired
    public ProInfoServiceImpl(ProductInfoRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public ProductInfo findByInfoId(String infoId) {
        if (repository.findById(infoId).isPresent()) {
            return repository.findById(infoId).get();
        }
        return null;
    }
    
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
    
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
    
}
