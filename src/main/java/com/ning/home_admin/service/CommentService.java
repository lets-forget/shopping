package com.ning.home_admin.service;

import com.ning.home_admin.bean.Comment;
import com.ning.home_admin.bean.Order;
import com.ning.home_admin.bean.ov.CommentPageResult;
import com.ning.home_admin.sytem.pojo.User;

import java.util.List;


public interface CommentService {
    void updateImage(Integer uid,Integer details_id,String ImageAddress,Integer id);

    void insertComment(Comment comment, User user);

    CommentPageResult findByPageComment(Integer currentPage, Integer limit, Integer details_id);

    List<Order> ispurchase(Integer userId, Integer detailsId);

    List<Order> daieValuateByUid(Integer userId);

}
