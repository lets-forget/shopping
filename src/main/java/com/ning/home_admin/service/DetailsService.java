package com.ning.home_admin.service;


import com.ning.home_admin.bean.Details;

public interface DetailsService {
    Details selectByPrimaryKey(Integer itemDetailsId);
}
