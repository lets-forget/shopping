package com.ning.home_admin.bean;

import com.github.pagehelper.PageInfo;
import com.ning.home_admin.commons.utils.DateUtil;
import com.ning.home_admin.sytem.pojo.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Comment {
    private Integer commentId;

    private Integer userId;

    private Integer detailsId;

    private String commentConnect;

    private Date commentTime;

    private String commentImage;

    private String commentTimeStr;

    public String getCommentTimeStr() {
        if (commentTime != null) commentTimeStr= DateUtil.formatFullTime(commentTime);
        return commentTimeStr;
    }

    public void setCommentTimeStr(String commentTimeStr) {
        this.commentTimeStr = commentTimeStr;
    }

    private User user;

    private List<Order> orders;

}