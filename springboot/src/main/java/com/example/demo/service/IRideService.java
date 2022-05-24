package com.example.demo.service;
/*
 ** @autor cc
 ** @date 2022/3/29
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Ride;
import org.springframework.web.bind.annotation.RequestBody;

public interface IRideService {
    public Result login(Ride ride);
    public Result addRide(Ride ride);
    public Result selectPage(Integer pageNum,Integer pageSize,String search,Integer id);
    public Result editRide(Ride ride);
    public Result deleteRide(long id);
    public Result getRideList(int id);
    public Result getDataById(int id);
    public Result isOnly(String username);
}
