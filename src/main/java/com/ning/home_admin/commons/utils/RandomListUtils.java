package com.ning.home_admin.commons.utils;

import com.ning.home_admin.bean.Item;
import com.ning.home_admin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomListUtils {

    @Autowired
    private ItemService itemService;

    Random random=new Random();

    public Long conct(){//查询总记录数
        return itemService.selectconct();
    }
    public List<Item> randomItem(){
        Integer conct = conct().intValue();
        Integer currentPage=random.nextInt(conct);
        Integer pageSize=9;
        if (currentPage+9>conct){
            currentPage=conct-9;
        }
        List<Item> listByCid = itemService.findListByCid(currentPage, pageSize);
        return listByCid;
    }
    //猜你喜欢和为你推荐
    public List<Item> hobbyAndrecommend(){
        Integer conct = conct().intValue();
        Integer currentPage=random.nextInt(conct);
        Integer pageSize=12;
        if (currentPage+12>conct){
            currentPage=conct-12;
        }
        return itemService.findListByCid(currentPage,pageSize);
    }
}
