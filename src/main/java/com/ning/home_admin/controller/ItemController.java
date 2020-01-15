package com.ning.home_admin.controller;


import com.ning.home_admin.bean.Item;
import com.ning.home_admin.bean.ov.Page;
import com.ning.home_admin.bean.ov.ShowAllDetails;

import com.ning.home_admin.commons.utils.RandomListUtils;
import com.ning.home_admin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    RandomListUtils randomListUtils;

    //显示详情页的信息
    @RequestMapping("/details/{item_id}")
    public String details(@PathVariable("item_id")
                                      Integer item_id,Model model){
        if (StringUtils.isEmpty(item_id)){
            return "redirect:/index";
        }
        ShowAllDetails showAllDetails=itemService.selectAllDetailsAndSmallAndBig(item_id);
        List<Item> items = randomListUtils.hobbyAndrecommend();
        List<Item> itemList = randomListUtils.randomItem();
        model.addAttribute("showAllDetails",showAllDetails);
        model.addAttribute("items",items);
        model.addAttribute("itemList",itemList);
        return "proDetail";
    }

    //Ajax显示详情页的信息
    @RequestMapping("/Ajaxdetails")
    @ResponseBody
    public ShowAllDetails Ajaxdetails(Integer item_id){
        ShowAllDetails showAllDetails=itemService.selectAllDetailsAndSmallAndBig(item_id);
        return showAllDetails;
    }
    //墙式挂壁
    @RequestMapping("/paint/selectAll")
    public String selectAll1(@RequestParam(value = "cid",required = false)Integer cid,
                            @RequestParam(value = "currentPage",required = false)Integer currentPage,
                            Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=9;

        Page pageInfo = itemService.paintSelectAllByCid(cid,currentPage,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "paint";
    }

    //抱枕靠垫
    @RequestMapping("/bzproList/selectAll")
    public String selectAll9(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        if (sort==null||sort<=0||sort>5)sort=1;
        Integer pageSize=12;
        Page pageInfo = itemService.bzproListSelectAllByCid(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",pageInfo);
        return "bzproList";
    }

    //干花干艺
    @GetMapping("/proList/selectAll")
    public String selectAll5(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=12;
        Page page=itemService.proListSelectAllById(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",page);
        return "proList";
    }

    //手机自营
    @GetMapping("/zbproList/selectAll")
    public String selectAll8(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=12;
        Page page=itemService.zbproListSelectAllById(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",page);
        return "zbproList";
    }

    //电脑自营
    @GetMapping("/perfume/selectAll")
    public String selectAll2(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=12;
        Page page=itemService.perfumeSelectAllById(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",page);
        return "perfume";
    }

    //编程书籍
    @GetMapping("/idea/selectAll")
    public String selectAll3(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=12;
        Page page=itemService.ideaSelectAllById(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",page);
        return "idea";
    }

    //家用电器
    @GetMapping("/flowerDer/selectAll")
    public String selectAll4(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=12;
        Page page=itemService.flowerDerSelectAllById(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",page);
        return "flowerDer";
    }

    //男女服饰
    @GetMapping("/vase_proList/selectAll")
    public String selectAll6(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=12;
        Page page=itemService.vase_proListSelectAllById(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",page);
        return "vase_proList";
    }
    //电脑配件
    @GetMapping("/decoration/selectAll")
    public String selectAll7(@RequestParam(value = "cid",required = false)Integer cid,
                             @RequestParam(value = "currentPage",required = false)Integer currentPage,
                             Integer sort,Model model){
        if (cid > 9|| StringUtils.isEmpty(cid)) {
            cid=1;
        }
        if (currentPage==null||currentPage<=0)currentPage=1;
        Integer pageSize=12;
        Page page=itemService.decorationSelectAllById(cid,currentPage,pageSize,sort);
        model.addAttribute("pageInfo",page);
        return "decoration";
    }

    //搜索
    @RequestMapping("/search/selectAll")
    public String search(Integer currentPage,String searchname,String sort,Model model){
        if (currentPage==null||currentPage<=0)currentPage=1;
        if(org.apache.commons.lang3.StringUtils.isBlank(sort)||"null".equals(sort)){
            sort="1";
        }
        Integer pageSize=16;
        Page page =itemService.searchSelectAll(currentPage,pageSize,searchname,Integer.parseInt(sort));
        model.addAttribute("pageInfo",page);
        return "search";
    }

    //index
    @GetMapping(value = {"/","/index"})
    public String index(Model model){
        List<Item> randomItem = randomListUtils.randomItem();
        List<Item> hobbyAndrecommend = randomListUtils.hobbyAndrecommend();
        model.addAttribute("randomItem",randomItem);
        model.addAttribute("hobbyAndrecommend",hobbyAndrecommend);
        return "index";
    }

}
