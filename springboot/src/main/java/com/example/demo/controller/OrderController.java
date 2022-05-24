package com.example.demo.controller;
/*
 ** @autor cc
 ** @date 2022/3/27
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Order;
import com.example.demo.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController //返回json的controller
@RequestMapping("/order") //创建统一路由
public class OrderController {
    @Resource
    IOrderService orderService;

    //新增
    @PostMapping("/addOrder")
    public Result<?> addOrder(@RequestBody Order order){
        Result result = orderService.addOrder(order);
        return result;
    }

    //获取顾客所有订单
    @GetMapping("/getOrder")
    public Result<?> getOrder(@RequestParam String openid){
        Result result = orderService.getOrder(openid);
        return result;
    }

    //获取骑手所有订单
    @GetMapping("/getMyOrder")
    public Result<?> getMyOrder(@RequestParam int ride,String method){
        Result result = orderService.getMyOrder(ride,method);
        return result;
    }

    //分页查找
    @GetMapping
    public Result<?> getDishes(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam Integer id) {
        Result result = orderService.selectPage(pageNum, pageSize, id);
        return result;
    }

    //更新订单
    @PutMapping("/updateOrder")
    public Result<?> updateOrder(@RequestBody Order order){
        Result result = orderService.updateOrder(order);
        return result;
    }

    //评价订单
    @GetMapping("/evalOrder")
    public Result<?> evalOrder(@RequestParam int oId,String eId){
        Result result = orderService.evalOrder(oId,eId);
        return result;
    }

    //获取月售
    @GetMapping("/getSales")
    public Result<?> getSales(@RequestParam String method,int id,String date,Integer pid){
        return orderService.getSales(method,id,date,pid);
    }
}
