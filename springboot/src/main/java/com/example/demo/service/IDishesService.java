package com.example.demo.service;
/*
 ** @autor cc
 ** @date 2022/3/20
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Dishes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IDishesService {
    public Result selectPage(Integer pageNum,Integer pageSize,String search,Integer id);
    public Result addDish(Dishes dishes);
    public Result editDish(Dishes dishes);
    public Result deleteDish(long id);
    public Result getDishes(int id);
    public Result getDataById(int id);
    public Result isOnly(String name);
}
