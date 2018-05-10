package cn.mongode.wxorder.exception;

import cn.mongode.wxorder.enums.ResultEnum;

/**
 * 订单异常的处理
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 包含：传参 - (ResultEnum resultEnum) 或 (Integer code,String message)
 */
public class OrderException extends RuntimeException {
    
    private Integer code;
    
    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    
    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
