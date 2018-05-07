package cn.mongode.wxorder.convertor;

import cn.mongode.wxorder.dataobject.OrderMaster;
import cn.mongode.wxorder.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

/**
 * @author: Mongo
 * @date: 2018/5/7
 * @description:
 */
public class OrderMasterToDTO {
    
    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }
    
}
