package cn.mongode.wxorder.service.impl;

import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.dto.CartDTO;
import cn.mongode.wxorder.enums.ProductStatusEnum;
import cn.mongode.wxorder.enums.ResultEnum;
import cn.mongode.wxorder.exception.OrderException;
import cn.mongode.wxorder.repository.ProductInfoRepository;
import cn.mongode.wxorder.service.ProInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品信息服务实现
 * @author: Mongo
 * @date: 2018/4/24
 * @description: 包含：查询单个商品、查询在架商品、查询所有商品、保存、加库存、减库存
 */
@Service
public class ProInfoServiceImpl implements ProInfoService {
    
    private final ProductInfoRepository repository;
    
    @Autowired
    public ProInfoServiceImpl(ProductInfoRepository repository) {
        this.repository = repository;
    }
    
    /**
     * 查询商品信息
     * @param infoId 商品信息id
     * @return 商品信息
     */
    @Override
    public ProductInfo findByInfoId(String infoId) {
        if (repository.findById(infoId).isPresent()) {
            return repository.findById(infoId).get();
        }
        return null;
    }
    
    /**
     * @return 所有在架商品
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
    
    /**
     * @param pageable (页码、条数)
     * @return 商品信息列表
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    /**
     * @param productInfo 商品信息
     * @return 保存的商品信息
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
    
    /**
     * 增加库存
     * @param cartDTOList 购物车DTO列表
     */
    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = findByInfoId(cartDTO.getProductId());
            if (productInfo == null) {
                throw new OrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
    
    /**
     * 减库存(事务控制)
     * @param cartDTOList 购物车DTO列表
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = findByInfoId(cartDTO.getProductId());
            if (productInfo == null) {
                throw new OrderException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new OrderException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}
