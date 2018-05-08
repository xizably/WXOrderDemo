package cn.mongode.wxorder.exception;

import cn.mongode.wxorder.enums.ResultEnum;

/**
 * @author: Mongo
 * @date: 2018/5/2
 * @description:
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
