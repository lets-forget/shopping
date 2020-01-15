package com.ning.home_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.ning.home_admin.bean.Order;
import com.ning.home_admin.bean.ov.Page;
import com.ning.home_admin.mapper.OrderMapper;
import com.ning.home_admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Integer insertOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public Order selectPrimayById(String orderId) {
        return orderMapper.selectByPrimaryById(orderId);
    }

    @Override
    public Integer updateByIsPay(String out_trade_no,String orderDetailsId) {
        return orderMapper.updateByIsPay(out_trade_no,orderDetailsId);
    }

    //分页显示所有订单
    @Override
    public Page selectAllByUid(Integer currentPage, Integer pageSize, String name, Integer uid, Integer orderType) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        //根据uid查询所有订单
        List<Order> orderList=orderMapper.selectAllByUid(uid,orderType,name);
        PageInfo<Order> pageInfo=new PageInfo<>(orderList);

        page.setList(orderList);
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public List<Order> selectByOrderId(String out_trade_no) {
        return orderMapper.selectByOrderId(out_trade_no);
    }

    @Override
    public Integer updateByOrderIds(List<Order> orderList) {
        Integer integer = orderMapper.updateByOrderIds(orderList);
        if (integer<=0){
            throw new RuntimeException("修改失败");
        }
        return integer;
    }
//修改收货
    @Override
    public Integer updateByOrderIsShouh(String orderId) {
        Integer integer = orderMapper.updateByOrderIsShouh(orderId);
        if (integer<=0){
            throw new RuntimeException("修改失败");
        }
        return integer;
    }


    @Override
    public Order selectByOrderIdAndOrderDetailsId(String orderId, String orderDetailsId) {

        return orderMapper.selectByOrderIdAndOrderDetailsId(orderId,orderDetailsId);
    }

    @Override
    public Integer updateByOrderIdAndOrderDetailsId(String orderId, String orderDetailsId) {
        Integer isupdate=orderMapper.updateByOrderIdAndOrderDetailsId(orderId,orderDetailsId);
        if (isupdate<=0){
            throw new RuntimeException("修改失败");
        }
        return isupdate;
    }
    //使用Ajax查询长度
    @Override
    public List<Order> selectAjaxlength(Integer id, Integer orderType) {
        return orderMapper.selectAjaxlength(id,orderType);
    }

    @Override
    public List<Order> findByispjAndUid(Integer userId) {
        return orderMapper.findByispjAndUid(userId);
    }
}
