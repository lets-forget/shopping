package com.ning.home_admin.controller;


import com.ning.home_admin.bean.CartItem;
import com.ning.home_admin.bean.ov.ResultInfo;
import com.ning.home_admin.bean.ov.ShoppingCart;
import com.ning.home_admin.commons.utils.UUIDUtils;
import com.ning.home_admin.service.ShoppingCartService;
import com.ning.home_admin.sytem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/cart")
@Controller
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    User user=null;

    @Autowired
    private UUIDUtils uuidUtils;

    @RequestMapping("/add")
    @ResponseBody
    public ResultInfo add(HttpServletRequest req, HttpServletResponse resp, CartItem cartItem){
        user = (User) req.getSession().getAttribute("user");
        cartItem.setCartid(uuidUtils.getUUID().replace("-",""));
        ResultInfo resultInfo  = shoppingCartService.addCart(req, resp, user, cartItem);
        return resultInfo;
    }
    /**
     * 进入首页
     * @return
     */
    @RequestMapping("/select")
    @ResponseBody
    public ShoppingCart toIndex(HttpServletRequest req, HttpServletResponse resp){

        user = (User) req.getSession().getAttribute("user");
        String key = shoppingCartService.getKey(req, resp, user);
        ShoppingCart cacheCart = shoppingCartService.mergeCart(key, user);

        return cacheCart;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ResultInfo remove(HttpServletRequest req, HttpServletResponse resp, @RequestBody List<CartItem> Classifys){
        user = (User) req.getSession().getAttribute("user");
        ResultInfo result = shoppingCartService.removeCart(req, resp, user, Classifys);
        return result;
    }

    //删除单个
    @RequestMapping("/removeByOne")
    public String removeByOne(HttpServletRequest req, HttpServletResponse resp,  CartItem item){
        user = (User) req.getSession().getAttribute("user");
        String result = shoppingCartService.removeByOne(req, resp, user, item);
        return result;
    }
    @RequestMapping("/updateCart")
    @ResponseBody
    public ResultInfo update(HttpServletRequest req, HttpServletResponse resp,String classify,String type){

        user = (User) req.getSession().getAttribute("user");
        ResultInfo resultInfo  = shoppingCartService.updateCart(req, resp, user,classify,type);
        return resultInfo;
    }

}
