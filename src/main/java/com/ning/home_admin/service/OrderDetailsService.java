package com.ning.home_admin.service;


import com.ning.home_admin.bean.OrderDetails;

public interface OrderDetailsService {
    Integer insertOrderDetails(OrderDetails orderDetails);

    OrderDetails selectByPrimaryKey(String orderId);
}
