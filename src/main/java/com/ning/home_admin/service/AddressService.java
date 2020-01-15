package com.ning.home_admin.service;


import com.ning.home_admin.bean.Address;

import java.util.List;

public interface AddressService {
    List<Address> selectAllByUid(Integer id);

    boolean saveAddress(Address address);

    Address selectByPrimark(Integer id);

    boolean updateAddressByid(Address address);

    boolean deleteAddressByid(Integer id);

    boolean updateDefaultById(Integer uid, Integer id);
}
