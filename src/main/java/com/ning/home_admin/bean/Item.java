package com.ning.home_admin.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Item implements Serializable {
    private Integer id;

    private Integer itemCid;

    private String itemTitle;

    private Double itemPrice;

    private String itemImage;

    private Integer itemSales;

    private Date itemCreatime;

}