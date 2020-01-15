package com.ning.home_admin.mapper;


import com.ning.home_admin.bean.Address;

import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    Address selectByPrimaryKey(Integer id);

    List<Address> selectAll();

    int updateByPrimaryKey(Address record);

    List<Address> selectAllByUid(Integer id);

    Integer updateDefaultValue(Integer uid);

    int updateDefultById(Integer id);
}