package com.ning.home_admin.service.impl;


import com.ning.home_admin.bean.Details;
import com.ning.home_admin.mapper.DetailsMapper;
import com.ning.home_admin.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsServiceImpl implements DetailsService {

    @Autowired
    private DetailsMapper detailsMapper;

    @Override
    public Details selectByPrimaryKey(Integer itemDetailsId) {
        return detailsMapper.selectByDetailsId(itemDetailsId);
    }
}
