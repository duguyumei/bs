package com.example.demo.service.Impl;
/*
 ** @autor cc
 ** @date 2022/3/25
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.Address;
import com.example.demo.mapper.AddressMapper;
import com.example.demo.service.IAddressService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceimpl implements IAddressService {
    @Resource
    AddressMapper addressMapper;

    @Override
    public Result addAddress(Address address){
        Integer res = addressMapper.insert(address);
        if (res > 0){
            return new Result().success(res);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result getAddress(String openid){
        Result result = new Result();
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        List<Address> addressList = addressMapper.selectList(queryWrapper);
        //list转json
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for(Address address:addressList){
            map = new HashMap<String, Object>();
            map.put("id", address.getId());
            map.put("addressname", address.getAddressname());
            map.put("name", address.getName());
            map.put("address", address.getAddress());
            map.put("latitude", address.getLatitude());
            map.put("longitude", address.getLongitude());
            map.put("openid", address.getOpenid());
            map.put("sex", address.getSex());
            map.put("time", address.getTime());
            map.put("message", address.getMessage());
            map.put("phone", address.getPhone());
            res.add(map);
        }
        Gson gson = new Gson();
        result.setCode("1");
        result.setMsg(gson.toJson(res));
        return result;
    }

    @Override
    public Result editAddress(Address address){
        Integer res = addressMapper.updateById(address);
        if(res > 0){
            return new Result().success();
        }
        return new Result().error("1","error");
    }

    @Override
    public Result deleteAddress(long id){
        Integer res = addressMapper.deleteById(id);
        if(res > 0){
            return new Result().success(res);
        }
        return new Result().error("1","error");
    }
}
