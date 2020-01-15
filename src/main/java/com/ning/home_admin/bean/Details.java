package com.ning.home_admin.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Details implements Serializable {
    private Integer itemDetailsId;

    private Integer itemDetailsItemid;

    private String itemDetailsNews;

    private Double itemDetailsPrice;

    private String itemDetailsTitle;

    private Integer itemDetailsCount;

}