package com.ning.home_admin.mapper;


import com.ning.home_admin.bean.ItemBig;

import java.util.List;

public interface ItemBigMapper {
    int deleteByPrimaryKey(Integer itemBigId);

    int insert(ItemBig record);

    ItemBig selectByPrimaryKey(Integer itemBigId);

    List<ItemBig> selectAll();

    int updateByPrimaryKey(ItemBig record);

    List<ItemBig> selectAllByDetails_id(Integer itemDetailsId);
}