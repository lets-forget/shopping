package com.ning.home_admin.controller;


import com.ning.home_admin.bean.Category;
import com.ning.home_admin.commons.exception.RedisConnectException;
import com.ning.home_admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/selectAll")
    public List<Category> selectAll() throws RedisConnectException {
        return categoryService.selectAll();
    }


}
