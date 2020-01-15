package com.ning.home_admin.controller;


import com.ning.home_admin.bean.Order;
import com.ning.home_admin.service.CommentService;
import com.ning.home_admin.service.OrderService;
import com.ning.home_admin.sytem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UrlController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private OrderService orderService;

    //登录
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    //注册
    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/forget")
    public String forget(){
        return "forget";
    }
    @RequestMapping("/search")
    public String search(){
        return "/search";
    }
    @RequestMapping("/header")
    public String header(){
        return "header";
    }
    @RequestMapping("/footer")
    public String footer(){
        return "footer";
    }
    @RequestMapping("/paint")
    public String paint(){
        return "paint";
    }
    @RequestMapping("/address")
    public String address(){
        return "redirect:/address/selectAll";
    }
    @RequestMapping("/gotop")
    public String gotop(){
        return "gotop";
    }
    @RequestMapping("/proDetail")
    public String proDetail(){
        return "proDetail";
    }
    @RequestMapping("/mygxin")
    public String mygxin(){
        return "mygxin";
    }
    @RequestMapping("/mygrxx")
    public String mygrxx(){
        return "mygrxx";
    }
    @RequestMapping("/remima")
    public String remima(){
        return "remima";
    }
    @RequestMapping("/boot")
    public String boot(){
        return "boot";
    }
    @RequestMapping("/cart")
    public String cart(){
        return "cart";
    }
    @RequestMapping("/order")
    public String order(){
        return "order";
    }
    @RequestMapping("/myorderq")
    public String myorderq(){
        return "redirect:/order/selectAll";
    }
    @RequestMapping("/ok")
    public String ok(){
        return "ok";
    }
    @RequestMapping("/gotoPagePay")
    public String gotoPagePage(){
        return "gotoPagePay";
    }
    @RequestMapping("/myprod")
    public String myprod(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        //未评价
        List<Order> wpj = commentService.daieValuateByUid(user.getUserId());
        //以评价
        List<Order> ypj=orderService.findByispjAndUid(user.getUserId());
        model.addAttribute("wpj",wpj);
        model.addAttribute("ypj",ypj);
        return "myprod";
    }
    @RequestMapping("/flowerDer")
    public String flowerDer(){
        return "flowerDer";
    }
    @RequestMapping("/proList")
    public String proList(){
        return "proList";
    }
    @RequestMapping("/vase_proList")
    public String vase_proList(){
        return "vase_proList";
    }
    @RequestMapping("/zbproList")
    public String zbproList(){
        return "zbproList";
    }
    @RequestMapping("/bzproList")
    public String bzproList(){
        return "bzproList";
    }
    @RequestMapping("/perfume")
    public String perfume(){
        return "perfume";
    }
    @RequestMapping("/idea")
    public String idea(){
        return "idea";
    }
    @RequestMapping("/decoration")
    public String decoration(){
        return "decoration";
    }
    @RequestMapping("/orderxq")
    public String orderxq(){
        return "orderxq";
    }
    @GetMapping("/success")
    public String success(){
        return "activate/ActivateSuccess";
    }
    @GetMapping("/fail")
    public String fail(){
        return "activate/ActivateFail";
    }
}
