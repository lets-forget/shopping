package com.ning.home_admin.service;




import com.ning.home_admin.bean.CartItem;
import com.ning.home_admin.bean.ov.ResultInfo;
import com.ning.home_admin.bean.ov.ShoppingCart;
import com.ning.home_admin.sytem.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ShoppingCartService {
    public String getKey(HttpServletRequest req, HttpServletResponse resp, User user);

    public ShoppingCart mergeCart(String tempKey, User user);

    public ResultInfo addCart(HttpServletRequest req, HttpServletResponse resp, User user, CartItem item);

    public ResultInfo removeCart(HttpServletRequest req, HttpServletResponse resp, User user, List<CartItem> item);

    String removeByOne(HttpServletRequest req, HttpServletResponse resp, User user, CartItem item);

    ResultInfo updateCart(HttpServletRequest req, HttpServletResponse resp, User user, String classify, String type);
}
