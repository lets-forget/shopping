package com.ning.home_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.ning.home_admin.bean.Details;
import com.ning.home_admin.bean.Item;
import com.ning.home_admin.bean.ItemBig;
import com.ning.home_admin.bean.ItemSmall;
import com.ning.home_admin.bean.ov.Page;
import com.ning.home_admin.bean.ov.ShowAllDetails;
import com.ning.home_admin.mapper.DetailsMapper;
import com.ning.home_admin.mapper.ItemBigMapper;
import com.ning.home_admin.mapper.ItemMapper;
import com.ning.home_admin.mapper.ItemSmallMapper;
import com.ning.home_admin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private DetailsMapper detailsMapper;

    @Autowired
    private ItemSmallMapper itemSmallMapper;

    @Autowired
    private ItemBigMapper itemBigMapper;

    @Override
    public Page paintSelectAllByCid(Integer cid, Integer currentPage, Integer pageSize) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items = itemMapper.paintSelectAllByCid(cid);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    //查询详情页的所有信息
    @Override
    public ShowAllDetails selectAllDetailsAndSmallAndBig(Integer item_id) {
        ShowAllDetails showAllDetails=new ShowAllDetails();

        //根据Item_id查询jd_details表
        Details details = detailsMapper.selectByPrimaryKey(item_id);
        //根据Details的id查询Small的图片
        List<ItemSmall> itemSmalls=itemSmallMapper.selectAllByDetails_id(details.getItemDetailsId());
        //根据Details的id查询Big的图片
        List<ItemBig> itemBigs=itemBigMapper.selectAllByDetails_id(details.getItemDetailsId());
        //封装数据
        showAllDetails.setDetails(details);
        showAllDetails.setSmallList(itemSmalls);
        showAllDetails.setBigList(itemBigs);
        return showAllDetails;
    }

    @Override
    public Page bzproListSelectAllByCid(Integer cid, Integer currentPage, Integer pageSize,Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items = itemMapper.bzproListSelectAllByCid(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    @Cacheable(cacheNames="book",key = "'bookInfo_'+#currentPage+#pageSize")
    public List<Item> findListByCid(Integer currentPage, Integer pageSize) {
        List<Item> itemList=itemMapper.selectAll(currentPage,pageSize);
        return itemList;
    }

    @Override
    public Page proListSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.proListSelectAllById(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Page zbproListSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.zbproListSelectAllById(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Page perfumeSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.perfumeSelectAllById(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Page ideaSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.ideaSelectAllById(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Page flowerDerSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.flowerDerSelectAllById(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Page vase_proListSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.vase_proListSelectAllById(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Page decorationSelectAllById(Integer cid, Integer currentPage, Integer pageSize, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.decorationSelectAllById(cid,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Page searchSelectAll(Integer currentPage,Integer pageSize, String name, Integer sort) {
        Page page=new Page();

        PageHelper.startPage(currentPage,pageSize);
        List<Item> items=itemMapper.searchSelectAll(name,sort);
        PageInfo<Item> pageInfo=new PageInfo<>(items);
        page.setList(pageInfo.getList());
        page.setPageInfo(pageInfo);
        return page;
    }

    @Override
    public Long selectconct() {
        return itemMapper.selectconct();
    }
}
