package com.ning.home_admin.service.impl;


import com.ning.home_admin.bean.OrderDetails;
import com.ning.home_admin.mapper.OrderDetailsMapper;
import com.ning.home_admin.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Override
    public Integer insertOrderDetails(OrderDetails orderDetails) {
        return orderDetailsMapper.insert(orderDetails);
    }

    @Override
    public OrderDetails selectByPrimaryKey(String orderId) {
        return orderDetailsMapper.selectByPrimaryKey(orderId);
    }
}
