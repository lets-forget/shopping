package com.ning.home_admin.service;


import com.ning.home_admin.bean.Category;
import com.ning.home_admin.commons.exception.RedisConnectException;

import java.util.List;

public interface CategoryService {
    List<Category> selectAll() throws RedisConnectException;
}
