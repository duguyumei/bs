package com.example.demo.controller;
/*
 ** @autor cc
 ** @date 2022/3/20
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Dishes;
import com.example.demo.service.IDishesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController //返回json的controller
@RequestMapping("/dishes") //创建统一路由
public class DishesController {
    @Resource
    IDishesService dishesService;

    //新增商品
    @PostMapping("/addDish")
    public Result<?> addDish(@RequestBody Dishes dishes){
        Result result = dishesService.addDish(dishes);
        return result;
    }

    //分页查找
    @GetMapping
    public Result<?> getDishes(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam Integer id) {
        Result result = dishesService.selectPage(pageNum, pageSize, search,id);
        return result;
    }

    //小程序根据商家获取菜品
    @GetMapping("/getDishes")
    public Result<?> getDishes(@RequestParam int id) {
        Result result = dishesService.getDishes(id);
        return result;
    }

    //根据菜品id获取信息
    @GetMapping("/getDataById")
    public Result<?> getDataById(@RequestParam int id) {
        Result result = dishesService.getDataById(id);
        return result;
    }

    //修改菜品
    @PutMapping("/editDish")
    public Result<?> editDish(@RequestBody Dishes dishes){
        Result result = dishesService.editDish(dishes);
        return result;
    }

    //删除菜品
    @DeleteMapping("/{id}")
    public Result<?> deleteDish(@PathVariable long id){
        Result result = dishesService.deleteDish(id);
        return result;
    }

    //判断是否唯一
    @GetMapping("/isOnly")
    public Result<?> isOnly(@RequestParam String name){
        Result result = dishesService.isOnly(name);
        return result;
    }
}
