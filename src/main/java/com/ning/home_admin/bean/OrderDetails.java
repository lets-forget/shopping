package com.ning.home_admin.bean;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    private Integer id;

    private String orderDetailsId;

    private Double orderDetailsPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId == null ? null : orderDetailsId.trim();
    }

    public Double getOrderDetailsPrice() {
        return orderDetailsPrice;
    }

    public void setOrderDetailsPrice(Double orderDetailsPrice) {
        this.orderDetailsPrice = orderDetailsPrice;
    }
}