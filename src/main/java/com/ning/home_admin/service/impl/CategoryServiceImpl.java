package com.ning.home_admin.service.impl;


import com.ning.home_admin.bean.Category;
import com.ning.home_admin.commons.exception.RedisConnectException;
import com.ning.home_admin.mapper.CategoryMapper;
import com.ning.home_admin.monitor.IRedisService;
import com.ning.home_admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Category> selectAll() throws RedisConnectException {
        String redisKey = "category";
        List<Category> listUser = null;
        //判断是否包含，如果有去redis去拿
        if(redisTemplate.hasKey(redisKey)) {
            return (List<Category>) redisTemplate.opsForValue().get(redisKey);
        } else {
            //如果没有去数据库拿
            listUser = categoryMapper.selectAll();
            redisTemplate.opsForValue().set(redisKey, listUser);
            return listUser;
        }

    }
}
