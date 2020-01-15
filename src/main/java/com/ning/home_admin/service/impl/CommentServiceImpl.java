package com.ning.home_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ning.home_admin.bean.Comment;
import com.ning.home_admin.bean.Order;
import com.ning.home_admin.bean.ov.CommentPageResult;
import com.ning.home_admin.mapper.CommentMapper;
import com.ning.home_admin.mapper.OrderMapper;
import com.ning.home_admin.service.CommentService;
import com.ning.home_admin.sytem.mapper.UserMapper;
import com.ning.home_admin.sytem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void updateImage(Integer uid, Integer details_id, String ImageAddress,Integer id) {
        commentMapper.updateImage(uid,details_id,ImageAddress,id);
    }

    @Override
    public void insertComment(Comment comment, User user) {
        comment.setCommentTime(new Date());
        comment.setUserId(user.getUserId());
        comment.setCommentImage("");
        commentMapper.insert(comment);
        orderMapper.updatePJByDetailIdAndUid(comment.getDetailsId(),user.getUserId());
    }

    @Override
    public CommentPageResult findByPageComment(Integer currentPage, Integer limit, Integer details_id) {
        CommentPageResult result=new CommentPageResult();
        PageHelper.startPage(currentPage,limit);
        List<Comment> comments = commentMapper.selectAll(details_id);
        PageInfo<Comment> info=new PageInfo<>(comments);
        result.setLists(comments);
        result.setTotal(info.getTotal());
        result.setCurrentPage(info.getPageNum());
        result.setLimit(info.getPageSize());
        return result;
    }

    @Override
    public List<Order> ispurchase(Integer userId, Integer detailsId) {
        return orderMapper.selectByUidAndDetailsId(userId,detailsId);
    }

    @Override
    public List<Order> daieValuateByUid(Integer userId) {
        return orderMapper.daieValuateByUid(userId);
    }

}
