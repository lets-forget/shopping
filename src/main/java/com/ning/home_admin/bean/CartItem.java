package com.ning.home_admin.bean;

import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {
    private Integer small_id;
    private String cartid;
    private String cartTitle;
    private String cartClassify;
    private String cartImage;
    private Double cartPrice;
    private Double cartSubtotal;
    private Integer cartCount;


    public Integer getSmall_id() {
        return small_id;
    }

    public void setSmall_id(Integer small_id) {
        this.small_id = small_id;
    }

    public String getCartTitle() {
        return cartTitle;
    }

    public void setCartTitle(String cartTitle) {
        this.cartTitle = cartTitle;
    }

    public String getCartClassify() {
        return cartClassify;
    }

    public void setCartClassify(String cartClassify) {
        this.cartClassify = cartClassify;
    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public Double getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(Double cartPrice) {
        this.cartPrice = cartPrice;
    }

    public Double getCartSubtotal() {
        return cartSubtotal;
    }

    public void setCartSubtotal(Double cartSubtotal) {
        this.cartSubtotal = cartSubtotal;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cartClassify, cartItem.cartClassify);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartClassify);
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartid='" + cartid + '\'' +
                ", cartTitle='" + cartTitle + '\'' +
                ", cartClassify='" + cartClassify + '\'' +
                ", cartImage='" + cartImage + '\'' +
                ", cartPrice=" + cartPrice +
                ", cartSubtotal=" + cartSubtotal +
                ", cartCount=" + cartCount +
                '}';
    }
}
