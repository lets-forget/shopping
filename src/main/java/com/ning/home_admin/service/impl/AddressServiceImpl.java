package com.ning.home_admin.service.impl;


import com.ning.home_admin.bean.Address;
import com.ning.home_admin.mapper.AddressMapper;
import com.ning.home_admin.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> selectAllByUid(Integer id) {
        return addressMapper.selectAllByUid(id);
    }

    @Override
    public boolean saveAddress(Address address) {
        int insert = addressMapper.insert(address);
        return insert>0?true:false;
    }

    @Override
    public Address selectByPrimark(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateAddressByid(Address address) {
        int isupdate = addressMapper.updateByPrimaryKey(address);
        if (isupdate<0){
            throw new RuntimeException("修改地址失败");
        }
        return true;
    }

    @Override
    public boolean deleteAddressByid(Integer id) {
        int delete = addressMapper.deleteByPrimaryKey(id);
        if (delete<0){
            throw new RuntimeException("删除失败");
        }
        return true;
    }
//修改默认地址
    @Override
    public boolean updateDefaultById(Integer uid,Integer id) {
        addressMapper.updateDefaultValue(uid);
        int updateById = addressMapper.updateDefultById(id);
        return updateById>0?true:false;
    }
}
