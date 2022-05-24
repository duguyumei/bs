package com.example.demo.service;
/*
 ** @autor cc
 ** @date 2022/3/27
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Order;

public interface IOrderService {
    public Result addOrder(Order order);
    public Result getOrder(String openid);
    public Result getMyOrder(int ride,String method);
    public Result selectPage(Integer pageNum,Integer pageSize,Integer id);
    public Result updateOrder(Order order);
    public Result evalOrder(int oId, String eId);
    public Result getSales(String method,int id, String date,Integer pid);
    public Result returEvaluate(int id);
}
