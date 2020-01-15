package com.ning.home_admin.bean;

import java.io.Serializable;

public class ItemBig implements Serializable {
    private Integer itemBigId;

    private Integer itemDetailsId;

    private String itemBigImage;

    public Integer getItemBigId() {
        return itemBigId;
    }

    public void setItemBigId(Integer itemBigId) {
        this.itemBigId = itemBigId;
    }

    public Integer getItemDetailsId() {
        return itemDetailsId;
    }

    public void setItemDetailsId(Integer itemDetailsId) {
        this.itemDetailsId = itemDetailsId;
    }

    public String getItemBigImage() {
        return itemBigImage;
    }

    public void setItemBigImage(String itemBigImage) {
        this.itemBigImage = itemBigImage == null ? null : itemBigImage.trim();
    }
}