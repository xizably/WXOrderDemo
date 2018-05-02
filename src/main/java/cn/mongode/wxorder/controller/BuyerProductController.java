package cn.mongode.wxorder.controller;

import cn.mongode.wxorder.VO.ProductInfoVO;
import cn.mongode.wxorder.VO.ProductVO;
import cn.mongode.wxorder.VO.ResultVO;
import cn.mongode.wxorder.dataobject.ProductCategory;
import cn.mongode.wxorder.dataobject.ProductInfo;
import cn.mongode.wxorder.service.CategoryService;
import cn.mongode.wxorder.service.ProInfoService;
import cn.mongode.wxorder.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 买家商品信息
 * @author: Mongo
 * @date: 2018/4/27
 * @description:
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    
    private final ProInfoService proInfoService;
    
    private final CategoryService categoryService;
    
    @Autowired
    public BuyerProductController(ProInfoService proInfoService, CategoryService categoryService) {
        this.proInfoService = proInfoService;
        this.categoryService = categoryService;
    }
    
    @GetMapping("/list")
    public ResultVO list() {
        /* 1.查询所有的上架商品 */
        List<ProductInfo> productInfoList = proInfoService.findUpAll();
        
        /* 2.查询类目（一次性查询） */
//        List<Integer> categoryTypeList = new ArrayList<>();
//        // 传统方法
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        // 精简方法(java8 , lamba)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        
        /* 3.数据拼装 */
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        
        return ResultVOUtil.success(productVOList);
    }
}
