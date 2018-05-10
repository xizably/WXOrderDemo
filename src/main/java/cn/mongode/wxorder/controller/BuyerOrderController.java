package cn.mongode.wxorder.controller;

import cn.mongode.wxorder.VO.ResultVO;
import cn.mongode.wxorder.convertor.OrderFormToDTOConvert;
import cn.mongode.wxorder.dto.OrderDTO;
import cn.mongode.wxorder.enums.ResultEnum;
import cn.mongode.wxorder.exception.OrderException;
import cn.mongode.wxorder.form.OrderForm;
import cn.mongode.wxorder.service.BuyerService;
import cn.mongode.wxorder.service.OrderService;
import cn.mongode.wxorder.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单控制器
 * @author: Mongo
 * @date: 2018/5/8
 * @description: 包含：创建订单、查询订单列表、查询单个订单、取消订单
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    
    private final OrderService orderService;
    private final BuyerService buyerService;
    
    public BuyerOrderController(OrderService orderService, BuyerService buyerService) {
        this.orderService = orderService;
        this.buyerService = buyerService;
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
        return new ResultVOUtil<Map<String, String>>().success(map);
    }
    
    // 查询订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(
            @RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        List<OrderDTO> orderDTOList = buyerService.findOrderList(openid, page, size).getContent();
        return new ResultVOUtil<List<OrderDTO>>().success(orderDTOList);
    }
    
    // 根据orderId查询订单(OrderDTO)
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) throws Exception {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return new ResultVOUtil<OrderDTO>().success(orderDTO);
    }
    
    // 根据orderId取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) throws Exception {
        buyerService.cancelOrder(openid, orderId);
        return new ResultVOUtil<OrderDTO>().success(null);
    }
}
