package com.ning.home_admin.mapper;


import com.ning.home_admin.bean.OrderDetails;

import java.util.List;

public interface OrderDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderDetails record);

    OrderDetails selectByPrimaryKey(String orderId);

    List<OrderDetails> selectAll();

    int updateByPrimaryKey(OrderDetails record);
}