package cn.mongode.wxorder.utils;

import cn.mongode.wxorder.VO.ResultVO;

/**
 * @author: Mongo
 * @date: 2018/5/2
 * @description:
 */
public class ResultVOUtil<T> {
    
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功!");
        resultVO.setData(object);
        return resultVO;
    }
    
    public static ResultVO success() {
        return success(null);
    }
    
    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
