package cn.mongode.wxorder.controller;

import cn.mongode.wxorder.VO.ResultVO;
import cn.mongode.wxorder.convertor.OrderFormToDTOConvert;
import cn.mongode.wxorder.dto.OrderDTO;
import cn.mongode.wxorder.enums.ResultEnum;
import cn.mongode.wxorder.exception.OrderException;
import cn.mongode.wxorder.form.OrderForm;
import cn.mongode.wxorder.service.CategoryService;
import cn.mongode.wxorder.service.OrderService;
import cn.mongode.wxorder.service.ProInfoService;
import cn.mongode.wxorder.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Mongo
 * @date: 2018/5/8
 * @description: 买家订单Controller.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    
    private final OrderService orderService;
    
    private final ProInfoService proInfoService;
    
    private final CategoryService categoryService;
    
    public BuyerOrderController(OrderService orderService,
                                ProInfoService proInfoService,
                                CategoryService categoryService) {
        this.orderService = orderService;
        this.proInfoService = proInfoService;
        this.categoryService = categoryService;
    }
    
    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm = {}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        
        OrderDTO orderDTO = OrderFormToDTOConvert.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }
        
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    
    // 订单列表
    
    // 订单详情
    
    // 取消订单
    
}
