package com.example.demo.controller;
/*
 ** @autor cc
 ** @date 2022/3/29
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Ride;
import com.example.demo.service.IRideService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ride")
public class RideController {
    @Resource
    IRideService rideService;

    //登录检测
    @PostMapping("/login") //定义一个pust接口,直接post对象
    public Result<?> login(@RequestBody Ride ride){
        Result result = rideService.login(ride);
        return result;
    }

    //新增骑手
    @PostMapping("/addRide")
    public Result<?> addRide(@RequestBody Ride ride){
        Result result = rideService.addRide(ride);
        return result;
    }

    //分页查找
    @GetMapping
    public Result<?> getRideInfo(@RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "") String search,
                                 @RequestParam Integer id){
        Result result = rideService.selectPage(pageNum, pageSize, search,id);
        return result;
    }

    //修改
    @PutMapping("/editRide")
    public Result<?> editRide(@RequestBody Ride ride){
        Result result = rideService.editRide(ride);
        return result;
    }

    //删除
    @DeleteMapping("/{id}")
    public Result<?> deleteRide(@PathVariable long id) {
        Result result = rideService.deleteRide(id);
        return result;
    }

    //获取骑手列表
    @GetMapping("/getRideList")
    public Result<?> getRideList(@RequestParam int id) {
        Result result = rideService.getRideList(id);
        return result;
    }

    //id获取
    @GetMapping("/getDataById")
    public Result<?> getDataById(@RequestParam String sid) {
        Integer id = Integer.parseInt(sid);
        Result result = rideService.getDataById(id);
        return result;
    }

    //账号唯一
    @GetMapping("/isOnly")
    public Result<?> isOnly(@RequestParam String username){
        Result result = rideService.isOnly(username);
        return result;
    }
}
