package com.example.demo.controller;
/*
 ** @autor cc
 ** @date 2022/3/12
 */

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Marchant;
import com.example.demo.mapper.MarchantMapper;
import com.example.demo.service.IEvaluateService;
import com.example.demo.service.IMarchantService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


@RestController //返回json的controller
@RequestMapping("/user") //创建统一路由
public class MarchantController {
    @Resource
    IMarchantService marchantService;
    @Resource
    IEvaluateService evaluateService;
    @Resource
    MarchantMapper marchantMapper;

    //注册新增
    @PostMapping //定义一个pust接口,直接post对象
    public Result<?> save(@RequestBody Marchant marchant){ //@RequestBody 将json参数转为java对象
        //通过service对象实现mapper中
        Result result = marchantService.register(marchant);
        return result;
    }

    //登录检测
    @PostMapping("/login") //定义一个pust接口,直接post对象
    public Result<?> login(@RequestBody Marchant marchant) {//@RequestBody 将json参数转为java对象
        Result result = marchantService.login(marchant);
        return result;
    }

    //根据账号名获取信息
    @PostMapping("/getDataByUsername")
    public Result<?> getDataByUsername(@RequestBody Marchant marchant) {
        Result result = marchantService.getDataByUsername(marchant);
        return result;
    }

    //根据id获取信息
    @GetMapping("/getDataById")
    public Result<?> getDataById(@RequestParam long id) {
        Result result = marchantService.getDataById(id);
        return result;
    }

    //查找有无相同店名或用户名
    @GetMapping("/isOnly")
    public Result<?> isOnly(@RequestParam String name,String username){
        Result result = marchantService.isOnly(name,username);
        return result;
    }

    //更新
    @PutMapping("/updateMarchant")
    public Result<?> update(@RequestBody Marchant marchant){
        marchantService.update(marchant);
        return Result.success();
    }

    //删除
    @DeleteMapping("/{id}")
    public Result<?> updata(@PathVariable long id){
        marchantMapper.deleteById(id);
        return Result.success();
    }

    //分页查找
    @GetMapping
    public Result<?> getMar(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Marchant> wrapper = Wrappers.<Marchant>lambdaQuery();
        if (StrUtil.isNotBlank(search)){//如果字符串为null,无法通过like检索
           wrapper.like(Marchant::getUsername,search);//模糊查询
        }
        Page<Marchant> marchantPage = marchantMapper.selectPage(
                new Page<>(pageNum,pageSize),wrapper);
        return Result.success(marchantPage);
    }

    //小程序查找
    @GetMapping("/getMarchant")
    public Result<?> getMarchant(@RequestParam(defaultValue = "") String search,String address) {
        return marchantService.getMarchant(search,address);
    }

    //更新评分
    @GetMapping("/updateScore")
    public Result<?> updateScore(@RequestParam int id){
        double res = evaluateService.getScore(id);
        return marchantService.updateScore(id,res);
    }

    //判断是否在派送范围
    @GetMapping("/isInArea")
    public Result<?> isInArea(@RequestParam String address, int marchant){
        return marchantService.isInArea(address,marchant);
    }
}
