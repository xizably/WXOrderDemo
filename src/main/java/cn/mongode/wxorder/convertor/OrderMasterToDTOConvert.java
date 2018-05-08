package cn.mongode.wxorder.convertor;

import cn.mongode.wxorder.dataobject.OrderMaster;
import cn.mongode.wxorder.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Mongo
 * @date: 2018/5/7
 * @description:
 */
public class OrderMasterToDTOConvert {
    
    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }
    
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(OrderMasterToDTOConvert::convert).collect(Collectors.toList());
    }
    
}
