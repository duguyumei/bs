package com.example.demo.service;
/*
 ** @autor cc
 ** @date 2022/4/4
 */

import com.example.demo.common.Result;
import com.example.demo.entity.Evaluate;
import org.springframework.web.bind.annotation.RequestBody;

public interface IEvaluateService {
    public Result addEvaluate(Evaluate evaluate);
    public Result getDataByOrder(int id);
    public double getScore(Integer id);
    public Result getAllEvaluate(String id);
    public Result returnEvaluate(int id, String message);
}
