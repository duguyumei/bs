package com.example.demo.service.Impl;
/*
 ** @autor cc
 ** @date 2022/3/27
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Dishes;
import com.example.demo.entity.Marchant;
import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.IOrderService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {
    @Resource
    OrderMapper orderMapper;

    @Override
    public Result selectPage(Integer pageNum,Integer pageSize,Integer id){
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        wrapper.eq(Order::getMarchant,id);
        wrapper.orderByDesc(Order::getStime);
        Page<Order> orderPage = orderMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(orderPage);
    }

    @Override
    public Result addOrder(Order order){
        order.setEvaluate("0");
        Integer res = orderMapper.insert(order);
        if (res > 0){
            return new Result().success(res);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result getOrder(String openid){
        Result result = new Result();
        List<Order> orderList;
        QueryWrapper<Order> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        orderList = orderMapper.selectList(queryWrapper);

        //list转json
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for(Order order : orderList){
            map = new HashMap<String, Object>();
            map.put("id", order.getId());
            map.put("url", order.getUrl());
            map.put("people", order.getPeople());
            map.put("openid", order.getOpenid());
            map.put("marchant", order.getMarchant());
            map.put("dish", order.getDish());
            map.put("ride", order.getRide());
            map.put("state", order.getState());
            map.put("money", order.getMoney());
            map.put("message", order.getMessage());
            map.put("pay", order.getPay());
            map.put("stime", order.getStime());
            map.put("etime", order.getEtime());
            map.put("evaluate", order.getEvaluate());
            map.put("address", order.getAddress());
            res.add(map);
        }
        Gson gson = new Gson();
        result.setCode("1");
        result.setMsg(gson.toJson(res));
        return result;
    }

    @Override
    public Result getMyOrder(int ride, String method){
        Result result = new Result();
        List<Order> orderList;
        QueryWrapper<Order> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("ride",ride);
        queryWrapper.eq(method.equals("ing"),"state",3);
        orderList = orderMapper.selectList(queryWrapper);

        //list转json
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for(Order order : orderList){
            map = new HashMap<String, Object>();
            map.put("id", order.getId());
            map.put("url", order.getUrl());
            map.put("people", order.getPeople());
            map.put("openid", order.getOpenid());
            map.put("marchant", order.getMarchant());
            map.put("dish", order.getDish());
            map.put("ride", order.getRide());
            map.put("state", order.getState());
            map.put("money", order.getMoney());
            map.put("message", order.getMessage());
            map.put("pay", order.getPay());
            map.put("stime", order.getStime());
            map.put("etime", order.getEtime());
            map.put("evaluate", order.getEvaluate());
            map.put("address", order.getAddress());
            res.add(map);
        }
        Gson gson = new Gson();
        result.setCode("1");
        result.setMsg(gson.toJson(res));
        return result;
    }

    @Override
    public Result updateOrder(Order order){
        Integer res = orderMapper.updateById(order);
        if(res > 0){
            return new Result().success(res);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result evalOrder(int oId, String eId){
        Order order = orderMapper.selectOne(
                Wrappers.<Order>lambdaQuery().eq(Order::getId,oId)
        );
        order.setEvaluate(eId);
        Integer res = orderMapper.updateById(order);
        if(res > 0){
            return new Result().success(res);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result getSales(String method,int id,String date, Integer pid){
        long time = new Date().getTime();
        String sl = String.valueOf(time - 2592000000L);
        String el = String.valueOf(time);
        Result result = new Result();
        if(method.equals("marchant")){
            List<Order> orderList = orderMapper.selectList(
                    Wrappers.<Order>lambdaQuery().between(date.equals("month"),Order::getStime,sl,el).eq(Order::getMarchant,id)
            );
            result.setData(orderList.size());
        }else if (method.equals("dish")){
            int sum = 0;
            List<Order> orderList = orderMapper.selectList(
                    Wrappers.<Order>lambdaQuery().between(date.equals("month"),Order::getStime,sl,el).eq(Order::getMarchant,pid)
            );
            for(Order order : orderList){
                String[] dishes = order.getDish().split(" ");
                for(int i = 0; i < dishes.length; i+=2){
                    if(dishes[i].equals(String.valueOf(id))){
                        sum += Integer.parseInt(dishes[i + 1]);
                        continue;
                    }
                }
            }
            result.setData(sum);
        }
        return result;
    }

    @Override
    public Result returEvaluate(int id){
        Order res = orderMapper.selectOne(
                Wrappers.<Order>lambdaQuery().eq(Order::getId,id)
        );
        res.setEvaluate("2");
        Integer num =  orderMapper.updateById(res);
        if(num > 0){
            return new Result().success();
        }
        return new Result().error("-1","error");
    }
}
