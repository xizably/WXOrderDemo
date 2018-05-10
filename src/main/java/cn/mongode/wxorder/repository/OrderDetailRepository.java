package cn.mongode.wxorder.repository;

import cn.mongode.wxorder.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详情Repository
 * @author: Mongo
 * @date: 2018/5/2
 * @description: 基本方法继承自JpaRepository, 添加的方法名要按照规则
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrderId(String orderId);
}
