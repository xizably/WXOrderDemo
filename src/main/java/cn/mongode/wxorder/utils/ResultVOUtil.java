package cn.mongode.wxorder.utils;

import cn.mongode.wxorder.VO.ResultVO;

/**
 * http请求返回的最外层对象工具
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 包含结果：成功、失败
 */
public class ResultVOUtil<T> {
    
    public ResultVO<T> success(T t) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功!");
        resultVO.setData(t);
        return resultVO;
    }
    
    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
