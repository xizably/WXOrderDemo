package cn.mongode.wxorder.VO;

/**
 * http请求返回的最外层对象
 * @author: Mongo
 * @date: 2018/4/27
 * @description:
 */
public class ResultVO<T> {
    
    /* 错误码. */
    private Integer code;
    
    /* 提示信息. */
    private String msg;
    
    /* 具体内容. */
    private T data;
    
    public ResultVO() {
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public T getData() {
        return data;
    }
}
