package cn.mongode.wxorder.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 订单的表单
 * @author: Mongo
 * @date: 2018/5/8
 * @description: 包含：买家信息、购物车 - 非空类属性添加了非空注解@NotEmpty,
 *               判定时在类名前使用@Valid进行验证, 如@Valid OrderForm orderForm
 */
@Data
public class OrderForm {
    
    /* 买家姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;
    
    /* 买家手机号 */
    @NotEmpty(message = "手机号必填")
    private String phone;
    
    /* 买家地址 */
    @NotEmpty(message = "地址必填")
    private String address;
    
    /* 买家微信openid */
    @NotEmpty(message = "openid必填")
    private String openid;
    
    /* 购物车 */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
