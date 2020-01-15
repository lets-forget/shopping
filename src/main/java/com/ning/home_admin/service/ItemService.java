package com.ning.home_admin.service;



import com.ning.home_admin.bean.Item;
import com.ning.home_admin.bean.ov.Page;
import com.ning.home_admin.bean.ov.ShowAllDetails;

import java.util.List;

public interface ItemService {
    Page paintSelectAllByCid(Integer cid, Integer currentPage, Integer pageSize);

    ShowAllDetails selectAllDetailsAndSmallAndBig(Integer item_id);

    Page bzproListSelectAllByCid(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    List<Item> findListByCid(Integer currentPage, Integer pageSize);

    Page proListSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    Page zbproListSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    Page perfumeSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    Page ideaSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    Page flowerDerSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    Page vase_proListSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    Page decorationSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort);

    Page searchSelectAll(Integer currentPage,Integer pageSize, String name, Integer sort);

    Long selectconct();
}
