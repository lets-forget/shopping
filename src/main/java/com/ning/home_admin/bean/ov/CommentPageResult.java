package com.ning.home_admin.bean.ov;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentPageResult {
    private List<?> lists=new ArrayList<>();
    private Long total;
    private Integer currentPage;
    private Integer limit;
}
