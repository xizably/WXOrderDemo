package cn.mongode.wxorder.controller;

import cn.mongode.wxorder.VO.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 买家商品信息
 * @author: Mongo
 * @date: 2018/4/27
 * @description:
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    
    @GetMapping("/list")
    public ResultVO list() {
        ResultVO resultVO = new ResultVO();
        return resultVO;
    }
}
