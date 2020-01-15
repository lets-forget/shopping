package com.ning.home_admin.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private Integer id;

    private Integer orderUid;

    private String orderId;

    private Integer orderAddressId;

    private String orderDetailsId;

    private String orderClassify;

    private String orderTitle;

    private Integer orderCount;

    private String orderImage;

    private Double orderPrice;

    private Double orderSubTotal;

    private String orderTime;

    private Integer orderShouh;

    private Integer orderIspay;

    private Integer orderType;

    private Integer orderPj;

    private String orderShouhStr;

    private String orderIspayStr;

    private String orderTypeStr;

    private Integer detailsId;

    public String getOrderShouhStr() {
        if (orderShouh==1){
            orderShouhStr="待评价";
        }
        if (orderShouh==0){
            orderShouhStr="待收货";
        }
        return orderShouhStr;
    }

    public void setOrderShouhStr(String orderShouhStr) {
        this.orderShouhStr = orderShouhStr;
    }

    public String getOrderIspayStr() {
        if (orderIspay==1){
            orderIspayStr="已支付";
        }
        if (orderIspay==0){
            orderIspayStr="待支付";
        }
        return orderIspayStr;
    }

    public void setOrderIspayStr(String orderIspayStr) {
        this.orderIspayStr = orderIspayStr;
    }

    public String getOrderTypeStr() {
        if (orderType==0){
            orderTypeStr="支付宝";
        }
        if (orderType==1){
            orderTypeStr="微信";
        }
        if (orderType==2){
            orderTypeStr="银联";
        }
        return orderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        this.orderTypeStr = orderTypeStr;
    }

}