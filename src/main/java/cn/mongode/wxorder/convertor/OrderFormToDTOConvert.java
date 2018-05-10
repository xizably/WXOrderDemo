package cn.mongode.wxorder.convertor;

import cn.mongode.wxorder.dataobject.OrderDetail;
import cn.mongode.wxorder.dto.OrderDTO;
import cn.mongode.wxorder.enums.ResultEnum;
import cn.mongode.wxorder.exception.OrderException;
import cn.mongode.wxorder.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * OrderForm转换为OrderDTO的转换器
 * @author: Mongo
 * @date: 2018/5/8
 * @description: OrderForm -> OrderDTO.
 */
@Slf4j
public class OrderFormToDTOConvert {
    
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
    
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (JsonSyntaxException e) {
            log.info("【对象转换】错误, string = {}", orderForm.getItems());
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
    
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
