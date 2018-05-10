package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主体Repository
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 基本方法继承自JpaRepository, 添加的方法名要按照规则
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    
}
