package com.example.demo.service.Impl;
/*
 ** @autor cc
 ** @date 2022/3/20
 */

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Dishes;
import com.example.demo.entity.Marchant;
import com.example.demo.mapper.DishesMapper;
import com.example.demo.service.IDishesService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DishesServiceImp implements IDishesService {
    @Resource
    DishesMapper dishesMapper;

    @Override
    public Result selectPage(Integer pageNum,Integer pageSize,String search,Integer id){
        LambdaQueryWrapper<Dishes> wrapper = Wrappers.<Dishes>lambdaQuery();
        if (StrUtil.isNotBlank(search)){//如果字符串为null,无法通过like检索
            wrapper.like(Dishes::getName, search);//模糊查询
        }
        wrapper.eq(Dishes::getMarchant, id);
        Page<Dishes> dishesPage = dishesMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(dishesPage);
    }

    @Override
    public Result getDishes(int id){
        Result result = new Result();
        String sid = String.valueOf(id);
        List<Dishes> dishesList;
        QueryWrapper<Dishes> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("marchant",sid);
        queryWrapper.eq("state","1");
        dishesList = dishesMapper.selectList(queryWrapper);

        //list转json
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for(Dishes dish : dishesList){
            map = new HashMap<String, Object>();
            map.put("id", dish.getId());
            map.put("url", dish.getUrl());
            map.put("name", dish.getName());
//            map.put("praise", dish.getPraise());
//            map.put("sales", dish.getSales());
//            map.put("monthsales", dish.getMonthsales());
            map.put("money", dish.getMoney());
            map.put("state", dish.getState());
//            map.put("discount", dish.getDiscount());
            map.put("message", dish.getMessage());
            res.add(map);
        }
        Gson gson = new Gson();
        result.setCode("1");
        result.setMsg(gson.toJson(res));
        return result;
    }

    @Override
    public Result getDataById(int id){
        Dishes res = dishesMapper.selectOne(
                Wrappers.<Dishes>lambdaQuery().eq(Dishes::getId,id));
        if(res==null) {
            Result result = Result.error("-1","商品以下架");
            result.setData("商品以下架");
            return result;
        }
        return Result.success(res);
    }

    @Override
    public Result addDish(Dishes dishes){
        Integer insertDish = dishesMapper.insert(dishes);
        if(insertDish > 0){
            return new Result().success(insertDish);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result editDish(Dishes dishes){
        Integer editDish = dishesMapper.updateById(dishes);
        if(editDish > 0){
            return new Result().success(editDish);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result deleteDish(long id){
        Integer deleteDish = dishesMapper.deleteById(id);
        if(deleteDish > 0){
            return new Result().success(deleteDish);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result isOnly(String name){
        Dishes res = dishesMapper.selectOne(
                Wrappers.<Dishes>lambdaQuery().eq(Dishes::getName,name)
        );
        if(res != null){
            return Result.error("-1","菜名重复");
        }
        return Result.success();
    }
}
