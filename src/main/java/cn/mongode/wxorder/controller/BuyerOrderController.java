package cn.mongode.wxorder.controller;

import cn.mongode.wxorder.VO.ResultVO;
import cn.mongode.wxorder.convertor.OrderFormToDTOConvert;
import cn.mongode.wxorder.dto.OrderDTO;
import cn.mongode.wxorder.enums.ResultEnum;
import cn.mongode.wxorder.exception.OrderException;
import cn.mongode.wxorder.form.OrderForm;
import cn.mongode.wxorder.service.OrderService;
import cn.mongode.wxorder.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
    
    public BuyerOrderController(OrderService orderService) {
        this.orderService = orderService;
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
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
    
        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findOrderList(openid, request);
    
        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    
    // 订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("orderId") String orderId) throws Exception {
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】 orderId为空");
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
    
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        return ResultVOUtil.success(orderDTO);
    }
    
    // 取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("orderId") String orderId) throws Exception {
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】 orderId为空");
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
    
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        orderService.cancel(orderDTO);
        orderDTO = orderService.findByOrderId(orderId);
        return ResultVOUtil.success();
    }
    
}
