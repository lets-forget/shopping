package com.ning.home_admin.controller;


import com.ning.home_admin.bean.*;
import com.ning.home_admin.bean.ov.DetailsInfo;
import com.ning.home_admin.bean.ov.Page;
import com.ning.home_admin.bean.ov.ShoppingCart;
import com.ning.home_admin.commons.utils.DateUtil;
import com.ning.home_admin.commons.utils.UUIDUtils;
import com.ning.home_admin.mapper.ItemSmallMapper;
import com.ning.home_admin.service.*;
import com.ning.home_admin.sytem.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ItemSmallMapper itemSmallMapper;

    @Autowired
    private DetailsService detailsService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UUIDUtils uuidUtils;

    @Autowired
    private OrderDetailsService orderDetailsService;
    /*支付*/
    @RequestMapping("/info")
    public String info(@RequestParam(required = false, defaultValue = "0") Integer small_id,
                       @RequestParam(required = false, defaultValue = "1") Integer count, HttpServletRequest request){

        request.getSession().removeAttribute("detailsInfo");
        request.getSession().removeAttribute("itemList");
        Order order=new Order();
        //获取session中的用户
        User session_user = (User) request.getSession().getAttribute("user");

        DetailsInfo detailsInfo=new DetailsInfo();

        //根据small_id查询小图片的信息
        ItemSmall itemSmall = itemSmallMapper.selectByPrimaryKey(small_id);

        //根据details_id查询商品信息
        Details details=detailsService.selectByPrimaryKey(itemSmall.getItemDetailsId());

        //根据uid查询地址
        List<Address> addressList=addressService.selectAllByUid(session_user.getId());
        for (Address address : addressList) {
            if (address.getDefaultvalue()==1){
                order.setOrderAddressId(address.getId());
            }
        }
        //封装订单参数
        order.setOrderUid(session_user.getId());
        order.setOrderId(uuidUtils.getOrderId());
        order.setOrderDetailsId(uuidUtils.getOrderId());
        order.setOrderClassify(itemSmall.getItemSmallAlt());
        order.setOrderTitle(details.getItemDetailsTitle());
        order.setOrderCount(count);
        order.setOrderImage(itemSmall.getItemSmallImage());
        order.setOrderPrice(details.getItemDetailsPrice());
        order.setOrderSubTotal(details.getItemDetailsPrice()*count);
        order.setOrderTime(DateUtil.formatFullTime(new Date()));
        order.setOrderShouh(0);
        order.setOrderIspay(0);
        order.setOrderType(0);
        order.setOrderPj(0);
        order.setDetailsId(details.getItemDetailsId());
        //保存订单
        orderService.insertOrder(order);
        //查询订单
        Order new_order=orderService.selectPrimayById(order.getOrderId());
        //封装数据
        detailsInfo.setDetails(details);
        detailsInfo.setItemSmall(itemSmall);
        detailsInfo.setOrder(order);
        detailsInfo.setCount(count);
        detailsInfo.setSubtotal(details.getItemDetailsPrice()*count);
        request.getSession().setAttribute("addressList",addressList);
        request.getSession().setAttribute("detailsInfo",detailsInfo);
        request.getSession().setAttribute("type","info");
        return "redirect:/order";
    }

    /*购物车支付*/
    @RequestMapping("/cartinfo")
    public String  cartinfo(String classifys, HttpServletRequest req, HttpServletResponse resp){

        req.getSession().removeAttribute("detailsInfo");
        User user = (User) req.getSession().getAttribute("user");
        String[] split = classifys.split(",");
        List<CartItem> itemList=new ArrayList<>();

        DetailsInfo detailsInfo=new DetailsInfo();


        //根据uid查询地址
        List<Address> addressList=addressService.selectAllByUid(user.getId());
        OrderDetails orderDetails=new OrderDetails();

        String orderId = uuidUtils.getOrderId();
        orderDetails.setOrderDetailsId(orderId);

        Order order=new Order();

        String key = shoppingCartService.getKey(req, resp, user);
        ShoppingCart cacheCart = shoppingCartService.mergeCart(key, user);
        double Totalmoney=0;
        for (String classify : split) {
            for (CartItem cartItem : cacheCart.getCartItems()) {
                if (classify.equals(cartItem.getCartid())){

                    //根据small_id查询小图片的信息
                    ItemSmall itemSmall = itemSmallMapper.selectByPrimaryKey(cartItem.getSmall_id());

                    //根据details_id查询商品信息
                    Details details=detailsService.selectByPrimaryKey(itemSmall.getItemDetailsId());

                    cartItem.setCartSubtotal(cartItem.getCartPrice()*cartItem.getCartCount());
                    itemList.add(cartItem);
                    Totalmoney+=cartItem.getCartSubtotal();
                    for (Address address : addressList) {
                        if (address.getDefaultvalue()==1){
                            order.setOrderAddressId(address.getId());
                        }
                    }
                    //封装订单参数
                    order.setOrderUid(user.getId());
                    order.setOrderId(orderId);
                    order.setOrderDetailsId(uuidUtils.getOrderId());
                    order.setOrderClassify(itemSmall.getItemSmallAlt());
                    order.setOrderTitle(details.getItemDetailsTitle());
                    order.setOrderCount(cartItem.getCartCount());
                    order.setOrderImage(itemSmall.getItemSmallImage());
                    order.setOrderPrice(details.getItemDetailsPrice());
                    order.setOrderSubTotal(details.getItemDetailsPrice()*cartItem.getCartCount());
                    order.setOrderTime(DateUtil.formatFullTime(new Date()));
                    order.setOrderShouh(0);
                    order.setOrderIspay(0);
                    order.setOrderType(0);
                    order.setOrderPj(0);
                    order.setDetailsId(details.getItemDetailsId());
                    //保存订单
                    orderService.insertOrder(order);

                }
            }
        }
        orderDetails.setOrderDetailsPrice(Totalmoney);
        detailsInfo.setOrder(order);
        orderDetailsService.insertOrderDetails(orderDetails);
        req.getSession().setAttribute("addressList",addressList);
        req.getSession().setAttribute("Totalmoney",Totalmoney);
        req.getSession().setAttribute("type","cart");
        req.getSession().setAttribute("detailsInfo",detailsInfo);
        req.getSession().setAttribute("itemList",itemList);
        return "redirect:/order";
    }

    //刷新地址
    @RequestMapping("/refreshAddress")
    public String refreshAddress(HttpSession session){
        User session_user = (User) session.getAttribute("user");
        List<Address> addressList = addressService.selectAllByUid(session_user.getId());
        session.setAttribute("addressList",addressList);
        return "order";
    }
    //删除地址
    @RequestMapping("/deleteAddressById")
    @ResponseBody
    public boolean deleteAddressById(Integer id){
        boolean b = addressService.deleteAddressByid(id);
        return b;
    }
    //修改订单地址
    @RequestMapping("/updateAddress")
    @ResponseBody
    public boolean updateAddress(Address address){
        boolean b = addressService.updateAddressByid(address);
        return b;
    }
    //添加地址
    @RequestMapping("/addAddress")
    @ResponseBody
    public boolean addAddress(Address address,HttpSession session){
        User session_user = (User) session.getAttribute("user");
        address.setUid(session_user.getId());
        address.setDefaultvalue(0);
        boolean insertAdd=addressService.saveAddress(address);
        if (!insertAdd){
            throw new RuntimeException("添加失败");
        }
        return insertAdd;
    }
    //修改默认
    @RequestMapping("/setDefault")
    @ResponseBody
    public boolean setDefault(Integer id,HttpSession session){
        User session_user= (User) session.getAttribute("user");
        boolean isupdate=addressService.updateDefaultById(session_user.getId(),id);
        return isupdate;
    }

    //查询全部订单
    @RequestMapping("/selectAll")
    public String selectAll(Integer currentPage,String orderType,String name, HttpSession session, Model model){
        if (currentPage==null||currentPage<=0)currentPage=1;
        if (StringUtils.isBlank(orderType)||"null".equals(orderType))orderType="1";
        Integer pageSize=3;
        User session_user = (User) session.getAttribute("user");
        Page page=orderService.selectAllByUid(currentPage,pageSize,name,session_user.getId(),Integer.parseInt(orderType));
        page.setUser(session_user);
        page.setOrderType(Integer.parseInt(orderType));
        model.addAttribute("pageInfo",page);
        return "myorderq";
    }

    @RequestMapping("/ajaxSelectAll")
    @ResponseBody
    public List<Order> ajaxSelectAll(Integer orderType, HttpSession session){
        if (orderType==null||orderType<=0||orderType>4)orderType=1;
        User session_user = (User) session.getAttribute("user");
        List<Order> orderList=orderService.selectAjaxlength(session_user.getId(),orderType);
        return orderList;
    }

    //确认收货
    @RequestMapping("/affirm/{orderId}/{orderDetailsId}")
    public String affirm(@PathVariable("orderId") String orderId,@PathVariable("orderDetailsId")String orderDetailsId){
        orderService.updateByOrderIdAndOrderDetailsId(orderId,orderDetailsId);
        return "redirect:/order/selectAll";
    }

    //订单详情
    @RequestMapping("/details/{orderId}/{orderDetailsId}")
    public String Orderdetails(@PathVariable("orderId") String orderId,@PathVariable("orderDetailsId")
            String orderDetailsId,Model model){

        Order order=orderService.selectByOrderIdAndOrderDetailsId(orderId,orderDetailsId);
        Address address=addressService.selectByPrimark(order.getOrderAddressId());
        model.addAttribute("order",order);
        model.addAttribute("address",address);
        return "orderxq";
    }

}
