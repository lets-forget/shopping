package com.ning.home_admin.controller;


import com.ning.home_admin.bean.Address;
import com.ning.home_admin.commons.exception.LoginException;
import com.ning.home_admin.commons.utils.GeneralResultInfo;
import com.ning.home_admin.service.AddressService;
import com.ning.home_admin.sytem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    //查询全部
    @RequestMapping("/selectAll")
    public String selectAll(HttpSession session, Model model){
        User session_user= (User) session.getAttribute("user");
        List<Address> addressList=addressService.selectAllByUid(session_user.getId());
        model.addAttribute("addressList",addressList);
        return "address";
    }
    //根据uid查询地址
    @PostMapping("/isByUidAddress")
    @ResponseBody
    public GeneralResultInfo isByUidAddress(HttpSession session) throws LoginException {
        User session_user= (User) session.getAttribute("user");
        List<Address> addressList=addressService.selectAllByUid(session_user.getId());
        if (CollectionUtils.isEmpty(addressList))
            throw new LoginException("该用户没有地址");

        return new GeneralResultInfo().success();
    }
    //添加地址
    @RequestMapping("/addAddress")
    public String addAddress(Address address,HttpSession session){
        User session_user = (User) session.getAttribute("user");
        address.setUid(session_user.getId());
        List<Address> addressList=addressService.selectAllByUid(session_user.getId());
        if (addressList.size()<=0){
            address.setDefaultvalue(1);
        }else{
            address.setDefaultvalue(0);
        }
        boolean insertAdd=addressService.saveAddress(address);
        if (!insertAdd){
            throw new RuntimeException("添加失败");
        }
        return "redirect:/address";
    }
    //回显地址
    @RequestMapping("/echoAddress")
    @ResponseBody
    public Address echoAddress(Integer id){
        Address address=addressService.selectByPrimark(id);
        return address;
    }
    //修改地址
    @RequestMapping("/updateAddress")
    public String updateAddress(Address address){
        boolean isupdate=addressService.updateAddressByid(address);
        return "redirect:/address";
    }
    //删除地址
    @RequestMapping("/deleteAddressByid")
    public String deleteAddressByid(@RequestParam(value = "id",required = false) Integer id){
        if (StringUtils.isEmpty(id)){
            throw new RuntimeException("请填写信息");
        }
        boolean isdelete=addressService.deleteAddressByid(id);
        return "redirect:/address";
    }
}
