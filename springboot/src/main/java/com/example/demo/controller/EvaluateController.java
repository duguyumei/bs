package com.example.demo.controller;
/*
 ** @autor cc
 ** @date 2022/4/4
 */


import com.example.demo.common.Result;
import com.example.demo.entity.Evaluate;
import com.example.demo.service.IEvaluateService;
import com.example.demo.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController //返回json的controller
@RequestMapping("/evaluate") //创建统一路由
public class EvaluateController {
    @Resource
    IEvaluateService evaluateService;
    @Resource
    IOrderService orderService;

    //新增
    @PostMapping("/addEvaluate")
    public Result<?> addEvaluate(@RequestBody Evaluate evaluate){
        Result result = evaluateService.addEvaluate(evaluate);
        return result;
    }

    //根据订单获取评价
    @GetMapping("/getDataByOrder")
    public Result<?> getDataByOrder(@RequestParam int id){
        Result result = evaluateService.getDataByOrder(id);
        return result;
    }

    //获取顾客所有评价信息
    @GetMapping("/getAllEvaluate")
    public Result<?> getAllEvaluate(@RequestParam String id){
        return evaluateService.getAllEvaluate(id);
    }

    //商家回复评价
    @GetMapping("/returnEvaluate")
    public Result<?> returnEvaluate(@RequestParam int id,String message){
        Result result = evaluateService.returnEvaluate(id,message);
        if(!result.getCode().equals("-1")){
            result =  orderService.returEvaluate(id);
        }
        return result;
    }
}
