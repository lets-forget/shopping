package com.ning.home_admin.mapper;


import com.ning.home_admin.bean.Details;

import java.util.List;

public interface DetailsMapper {
    int deleteByPrimaryKey(Integer itemDetailsId);

    int insert(Details record);

    Details selectByPrimaryKey(Integer itemDetailsId);

    List<Details> selectAll();

    int updateByPrimaryKey(Details record);

    Details selectByDetailsId(Integer itemDetailsId);
}