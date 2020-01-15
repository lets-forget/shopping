package com.ning.home_admin.bean.ov;

import com.github.pagehelper.PageInfo;
import com.ning.home_admin.sytem.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class Page {
    private PageInfo<?> pageInfo;
    private List<?> list;
    User user;
    Integer orderType;
}
