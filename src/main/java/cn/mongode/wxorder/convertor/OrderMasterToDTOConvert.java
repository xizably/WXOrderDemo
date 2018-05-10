package cn.mongode.wxorder.convertor;

import cn.mongode.wxorder.dataobject.OrderMaster;
import cn.mongode.wxorder.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderMaster转换为OrderDTO的转换器
 * @author: Mongo
 * @date: 2018/5/7
 * @description: 包含：OrderMaster -> OrderDTO ; List<OrderMaster> -> List<OrderDTO> ;
 */
public class OrderMasterToDTOConvert {
    
    private static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }
    
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(OrderMasterToDTOConvert::convert).collect(Collectors.toList());
    }
    
}
