package com.ning.home_admin.mapper;

import com.ning.home_admin.bean.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    Item selectByPrimaryKey(Integer id);

    List<Item> selectAll(Integer currentPage, Integer pageSize);

    int updateByPrimaryKey(Item record);

    List<Item> paintSelectAllByCid(Integer cid);

    List<Item> bzproListSelectAllByCid(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> proListSelectAllById(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> zbproListSelectAllById(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> perfumeSelectAllById(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> ideaSelectAllById(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> flowerDerSelectAllById(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> vase_proListSelectAllById(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> decorationSelectAllById(@Param("cid") Integer cid, @Param("sort") Integer sort);

    List<Item> searchSelectAll(@Param("name") String name, @Param("sort") Integer sort);

    Long selectconct();
}