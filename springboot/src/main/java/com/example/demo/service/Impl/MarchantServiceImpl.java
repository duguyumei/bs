package com.example.demo.service.Impl;
/*
 ** @autor cc
 ** @date 2022/3/12
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.LocationUtils;
import com.example.demo.common.Result;
import com.example.demo.entity.Marchant;
import com.example.demo.mapper.MarchantMapper;
import com.example.demo.service.IMarchantService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MarchantServiceImpl implements IMarchantService {
    @Resource
    MarchantMapper marchantMapper;


    //注册商家
    @Override
    public Result register(Marchant marchant){
        Integer insertMarchant = marchantMapper.insert(marchant);
        if(insertMarchant > 0){
            return new Result().success();
        }
        return new Result().error("1","error");
    }

    //登录商家
    @Override
    public Result login(Marchant marchant){
        //查找账号密码
        Marchant res = marchantMapper.selectOne(
                Wrappers.<Marchant>lambdaQuery().eq(Marchant::getUsername,marchant.getUsername()).eq(Marchant::getPassword,marchant.getPassword()));
        if(res==null) {
            return Result.error("-1","账号或密码错误");
        }
        return Result.success(res);
    }

    //根据账号查找用户信息
    @Override
    public Result getDataByUsername(Marchant marchant){
        Marchant res = marchantMapper.selectOne(
                Wrappers.<Marchant>lambdaQuery().eq(Marchant::getUsername,marchant.getUsername()));
        if(res==null) {
            return Result.error("-1","账号错误");
        }
        res.setPassword("0");
        return Result.success(res);
    }

    @Override
    public Result getDataById(long id){
        Marchant res = marchantMapper.selectOne(
                Wrappers.<Marchant>lambdaQuery().eq(Marchant::getId,id));
        if(res==null) {
            return Result.error("-1","账号错误");
        }
        res.setPassword("");
        return Result.success(res);
    }

    @Override
    public Result isOnly(String name,String username){
        Marchant isUsername = marchantMapper.selectOne(
                Wrappers.<Marchant>lambdaQuery().eq(Marchant::getUsername,username)
        );
        Marchant isName = marchantMapper.selectOne(
                Wrappers.<Marchant>lambdaQuery().eq(Marchant::getName,name)
        );
        if(isName!=null && isUsername != null){
            return Result.error("-1","账号和店名重复");
        }else if(isName!=null) {
            return Result.error("-1","店名重复");
        }else if(isUsername != null){
            return Result.error("-1","账号重复");
        }
        return Result.success();
    }

    //更新
    @Override
    public Result update(Marchant marchant){
        Integer num =  marchantMapper.updateById(marchant);
        if(num > 0){
            return new Result().success();
        }
        return new Result().error("1","error");
    }

    @Override
    public Result updateScore(int id,double score){
        Marchant res = marchantMapper.selectOne(
                Wrappers.<Marchant>lambdaQuery().eq(Marchant::getId,id));
        res.setScore(score);
        Integer num =  marchantMapper.updateById(res);
        if(num > 0){
            return new Result().success();
        }
        return new Result().error("1","error");
    }

    @Override
    public Result getMarchant(String search,String address){
        String arr[] = address.split(",");
        String lat1=arr[0],long1=arr[1];
        Result result = new Result();
        List<Marchant> marchantList;
        //如果有关键字
        if((!search.equals("")) && search != null){
            QueryWrapper<Marchant> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(search), "name", search);
            marchantList = marchantMapper.selectList(queryWrapper);
        }else {
            QueryWrapper<Marchant> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",1);
            marchantList = marchantMapper.selectList(queryWrapper);
        }
        //list转json
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        //先将list集合一条一条的放入map中
        for (Marchant marchant : marchantList) {
            Double distance = LocationUtils.getDistance(Double.valueOf(marchant.getLatitude()),Double.valueOf(marchant.getLongitude()),Double.valueOf(lat1),Double.valueOf(long1));
            if(distance / 1000 <= marchant.getArea()){
                map = new HashMap<String, Object>();
                map.put("id", marchant.getId());
                map.put("url", marchant.getUrl());
                map.put("name", marchant.getName());
                map.put("score", marchant.getScore());
//            map.put("sales", marchant.getSales());
                map.put("address", marchant.getAddress());
                map.put("message", marchant.getMessage());
                map.put("distance", distance / 1000);
                //再用List<Map<String, Object>> result 将之前的map装进来
                res.add(map);
            }
        }
        //将对象转化为json字符
        Gson gson = new Gson();
        result.setCode("1");
        result.setMsg(gson.toJson(res));
        return result;
    }

    @Override
    public Result isInArea(String address, int marchant){
        String arr[] = address.split(",");
        String lat1=arr[0],long1=arr[1];
        Result result = new Result();

        Marchant res = marchantMapper.selectOne(
                Wrappers.<Marchant>lambdaQuery().eq(Marchant::getId,marchant));

        Double distance = LocationUtils.getDistance(Double.valueOf(res.getLatitude()),Double.valueOf(res.getLongitude()),Double.valueOf(lat1),Double.valueOf(long1));
        if(distance / 1000 <= res.getArea()){
            result.setCode("0");
            result.setData("0");
        }else{
            result.setCode("1");
            result.setData("1");
        }
        return result;
    }
}
