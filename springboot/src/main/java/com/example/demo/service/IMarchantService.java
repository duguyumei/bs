package com.example.demo.service;
/*
 ** @autor cc
 ** @date 2022/3/12
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Marchant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface IMarchantService {
    public Result register(Marchant marchant);
    public Result login(Marchant marchant);
    public Result getDataByUsername(Marchant marchant);
    public Result getDataById(long id);
    public Result update(Marchant marchant);
    public Result getMarchant(String search,String address);
    public Result isOnly(String name,String username);
    public Result updateScore(int id, double res);
    public Result isInArea(String address, int marchant);
}
