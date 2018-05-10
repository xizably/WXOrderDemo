package cn.mongode.wxorder.VO;

/**
 * http请求返回的最外层对象
 * @author: Mongo
 * @date: 2018/4/27
 * @description: http请求返回的最外层对象(ViewObject - 表现层对象)
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {
    
    /* 错误码. */
    private Integer code;
    
    /* 提示信息. */
    private String msg = "";
    
    /* 具体内容. */
    private T data;
    
    public ResultVO() {
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}
