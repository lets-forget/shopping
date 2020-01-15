package com.ning.home_admin.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private Integer id;

    private Integer uid;

    private String name;

    private String telephone;

    private String province;

    private String city;

    private String county;

    private String particulars;

    private Integer defaultvalue;

}