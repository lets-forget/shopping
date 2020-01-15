package com.ning.home_admin.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.ning.home_admin.bean.Item;
import com.ning.home_admin.bean.Order;
import com.ning.home_admin.bean.OrderDetails;
import com.ning.home_admin.commons.config.AlipayConfig;
import com.ning.home_admin.commons.utils.RandomListUtils;
import com.ning.home_admin.service.OrderDetailsService;
import com.ning.home_admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PayController {

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailsService detailsService;

    @Autowired
    private RandomListUtils randomListUtils;

    @RequestMapping("/{orderId}/{orderDetailsId}/{type}")
    public String paymentPage(@PathVariable("orderId")String orderId,@PathVariable("orderDetailsId")String orderDetailsId,@PathVariable("type")String type, HttpServletResponse response, Model model, HttpSession session){

        try {
            AlipayClient alipayClient = new DefaultAlipayClient(
                    alipayConfig.getOpenApiDomain(),
                    alipayConfig.getAppId(),
                    alipayConfig.getMerchantPrivateKey(),
                    alipayConfig.getFormat(),
                    alipayConfig.getCharset(),
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getSignType()); //获得初始化的AlipayClient

            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
            alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());//在公共参数中设置回跳和通知地址
            //删除session中的购物车
            session.removeAttribute("detailsInfo");
            session.setAttribute("orderDetailsId",orderDetailsId);

            if (type.equals("info")){
                Order order = orderService.selectByOrderIdAndOrderDetailsId(orderId,orderDetailsId);
                alipayRequest.setBizContent("{" +
                        "    \"out_trade_no\":\""+order.getOrderId()+"\"," +
                        "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                        "    \"total_amount\":\""+order.getOrderSubTotal()+"\"," +
                        "    \"subject\":\""+order.getOrderClassify()+"\"," +
                        "    \"body\":\""+order.getOrderTitle()+"\"," +
                        "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                        "    \"extend_params\":{" +
                        "    \"sys_service_provider_id\":\"2088511833207846\"" +
                        "    }"+
                        "  }");//填充业务参数
            }else if (type.equals("cart")){
                OrderDetails orderDetails = detailsService.selectByPrimaryKey(orderId);
                alipayRequest.setBizContent("{" +
                        "    \"out_trade_no\":\""+orderDetails.getOrderDetailsId()+"\"," +
                        "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                        "    \"total_amount\":\""+orderDetails.getOrderDetailsPrice()+"\"," +
                        "    \"subject\":\"购物车\"," +
                        "    \"body\":\"宁康文\"," +
                        "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                        "    \"extend_params\":{" +
                        "    \"sys_service_provider_id\":\"2088511833207846\"" +
                        "    }"+
                        "  }");//填充业务参数
            }

            String form="";
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            session.setAttribute("type",type);
            response.setContentType("text/html;charset=" + alipayConfig.getCharset());
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception", "支付出错了!");
            return "exception";
        }
        return "ok";
    }

    @RequestMapping("/return")
    public String returnUrl(String out_trade_no,String total_amount,String body,
                            Model model,HttpSession session){
        if (StringUtils.isEmpty(out_trade_no)){
            return "redirect:/order";
        }
        OrderDetails orderDetails=detailsService.selectByPrimaryKey(out_trade_no);

        String type = (String) session.getAttribute("type");

        String orderDetailsId = (String) session.getAttribute("orderDetailsId");
        if (type.equals("info")){
            orderService.updateByIsPay(out_trade_no,orderDetailsId);
        }else if(type.equals("cart")){
            List<Order> orderList=orderService.selectByOrderId(out_trade_no);
            orderService.updateByOrderIds(orderList);
        }
        session.removeAttribute("orderDetailsId");
        session.removeAttribute("type");
        List<Item> items1 = randomListUtils.randomItem();
        List<Item> items2 = randomListUtils.hobbyAndrecommend();
        model.addAttribute("items1",items1);
        model.addAttribute("items2",items2);
        return "ok";
    }

    @RequestMapping("/notify")
    public void notifyUrl(){

        System.out.println("notify--------------------------");



    }

}
