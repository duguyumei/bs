package com.example.demo.service;
/*
 ** @autor cc
 ** @date 2022/3/25
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Address;

public interface IAddressService {
    public Result addAddress(Address address);
    public Result getAddress(String openid);
    public Result editAddress(Address address);
    public Result deleteAddress(long id);
}
