package com.example.demo.controller;
/*
 ** @autor cc
 ** @date 2022/3/25
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Address;
import com.example.demo.entity.Dishes;
import com.example.demo.service.IAddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController //返回json的controller
@RequestMapping("/address") //创建统一路由
public class AddressController {
    @Resource
    IAddressService addressService;

    //新增地址
    @PostMapping("/addAddress")
    public Result<?> addAddress(@RequestBody Address address){
        Result result = addressService.addAddress(address);
        return result;
    }

    //获取地址
    @GetMapping("/getAddress")
    public Result<?> getAddress(@RequestParam String openid){
        Result result = addressService.getAddress(openid);
        return result;
    }

    //修改地址
    @PutMapping("/editAddress")
    public Result<?> editAddress(@RequestBody Address address){
        Result result = addressService.editAddress(address);
        return result;
    }

    //删除地址
    @DeleteMapping("/deleteAddress/{id}")
    public Result<?> deleteDish(@PathVariable long id){
        Result result = addressService.deleteAddress(id);
        return result;
    }
}
