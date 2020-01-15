package com.ning.home_admin.mapper;

import com.ning.home_admin.bean.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    Order selectByPrimaryById(String orderId);

    Integer updateByIsPay(@Param("orderId") String out_trade_no, @Param("orderDetailsId") String orderDetailsId);

    List<Order> selectAllByUid(@Param("uid") Integer uid, @Param("orderType") Integer orderType, @Param("name") String name);

    List<Order> selectByOrderId(String out_trade_no);

    Integer updateByOrderIds(List<Order> orderList);

    Integer updateByOrderIsShouh(String orderId);

    Order selectByOrderIdAndOrderDetailsId(@Param("orderId") String orderId, @Param("orderDetailsId") String orderDetailsId);

    Integer updateByOrderIdAndOrderDetailsId(@Param("orderId") String orderId, @Param("orderDetailsId") String orderDetailsId);

    List<Order> selectAjaxlength(@Param("id") Integer id, @Param("orderType") Integer orderType);


    List<Order> selectByUidAndDetailsId(Integer uid,Integer details_id);

    List<Order> daieValuateByUid(Integer userId);

    void updatePJByDetailIdAndUid(Integer detailsId, Integer userId);

    List<Order> findByispjAndUid(Integer userId);
}