package com.example.demo.service.Impl;
/*
 ** @autor cc
 ** @date 2022/3/29
 */


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Dishes;
import com.example.demo.entity.Ride;
import com.example.demo.mapper.RideMapper;
import com.example.demo.service.IRideService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RideServiceImpl implements IRideService {
    @Resource
    RideMapper rideMapper;

    @Override
    public Result login(Ride ride){
        Ride res = rideMapper.selectOne(
                Wrappers.<Ride>lambdaQuery().eq(Ride::getUsername,ride.getUsername()).eq(Ride::getPassword,ride.getPassword())
        );
        if(res==null) {
            return Result.error("-1","账号或密码错误");
        }
        res.setPassword("");
        return Result.success(res);
    }

    public Result<?> addRide(Ride ride){
        Integer res = rideMapper.insert(ride);
        if(res > 0){
            return new Result().success(ride);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result selectPage(Integer pageNum,Integer pageSize,String search,Integer id){
        LambdaQueryWrapper<Ride> wrapper = Wrappers.<Ride>lambdaQuery();
        wrapper.like(Ride::getName,search);
        wrapper.eq(Ride::getMarchant,id);
        Page<Ride> ridePage = rideMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(ridePage);
    }

    @Override
    public Result editRide(Ride ride){
        Integer res = rideMapper.updateById(ride);
        if(res > 0){
            return new Result().success(ride);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result deleteRide(long id){
        Integer res = rideMapper.deleteById(id);
        if(res > 0){
            return new Result().success(res);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result getRideList(int id){
        Result result = new Result();
        String sid = String.valueOf(id);
        List<Ride> rideList;
        QueryWrapper<Ride> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("marchant",sid);
        rideList = rideMapper.selectList(queryWrapper);

        //list转json
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for(Ride ride : rideList){
            map = new HashMap<String, Object>();
            map.put("id", ride.getId());
            map.put("name", ride.getName());
            map.put("username", ride.getUsername());
            map.put("password", ride.getPassword());
            map.put("phone", ride.getPhone());
            map.put("marchant",ride.getMarchant());
            res.add(map);
        }
        Gson gson = new Gson();
        result.setCode("1");
        result.setMsg(gson.toJson(res));
        return result;
    }

    @Override
    public Result getDataById(int id){
        Ride res = rideMapper.selectOne(
                Wrappers.<Ride>lambdaQuery().eq(Ride::getId,id));
        if(res==null) {
            Result result = Result.error("-1","骑手信息以删除");
            result.setData("骑手信息以删除");
            return result;
        }
        return Result.success(res);
    }

    @Override
    public Result isOnly(String username){
        Ride res = rideMapper.selectOne(
                Wrappers.<Ride>lambdaQuery().eq(Ride::getUsername,username)
        );
        if(res != null){
            return Result.error("-1","账号重复");
        }
        return Result.success();
    }
}
