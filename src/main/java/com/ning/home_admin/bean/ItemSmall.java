package com.ning.home_admin.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemSmall implements Serializable {
    private Integer itemSmallId;

    private Integer itemDetailsId;

    private String itemSmallAlt;

    private String itemSmallImage;

}