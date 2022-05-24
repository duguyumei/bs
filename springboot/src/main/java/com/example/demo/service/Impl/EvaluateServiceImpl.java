package com.example.demo.service.Impl;
/*
 ** @autor cc
 ** @date 2022/4/4
 */

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Evaluate;
import com.example.demo.entity.Marchant;
import com.example.demo.entity.Order;
import com.example.demo.mapper.EvaluateMapper;
import com.example.demo.service.IEvaluateService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class EvaluateServiceImpl implements IEvaluateService {
    @Resource
    EvaluateMapper evaluateMapper;

    @Override
    public Result<?> addEvaluate(Evaluate evaluate){
        Integer res = evaluateMapper.insert(evaluate);
        if (res > 0){
            return new Result().success(evaluate);
        }
        return new Result().error("1","error");
    }

    @Override
    public Result getDataByOrder(int id){
        Evaluate res = evaluateMapper.selectOne(
                Wrappers.<Evaluate>lambdaQuery().eq(Evaluate::getOrderid,id)
        );
        if(res==null) {
            return Result.error("-1","账号错误");
        }
        return Result.success(res);
    }

    @Override
    public double getScore(Integer id){
        double sum = 0;
        double score = 0;
        List<Evaluate> list = evaluateMapper.selectList(
                Wrappers.<Evaluate>lambdaQuery().eq(Evaluate::getMarchant,id)
        );
        for (Evaluate evaluate : list){
            sum += evaluate.getSource();
        }
        if(list.size() != 0){
            score = sum / list.size();
            score = new BigDecimal(score).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return score;
    }

    @Override
    public Result getAllEvaluate(String id){
        Result result = new Result();
        List<Evaluate> list = evaluateMapper.selectList(
                Wrappers.<Evaluate>lambdaQuery().eq(Evaluate::getOpenid,id).orderByDesc(Evaluate::getStime)
        );
        //list转json
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        //先将list集合一条一条的放入map中
        for (Evaluate evaluate : list) {
            map = new HashMap<String, Object>();
            map.put("id", evaluate.getId());
            map.put("marchant", evaluate.getMarchant());
            map.put("source", evaluate.getSource());
            map.put("message", evaluate.getMessage());
            map.put("stime", evaluate.getStime());
            map.put("url", evaluate.getUrl());
            map.put("etime", evaluate.getEtime());
            map.put("marmessage", evaluate.getMarmessage());
            //再用List<Map<String, Object>> result 将之前的map装进来
            res.add(map);
        }
        //将对象转化为json字符
        Gson gson = new Gson();
        result.setCode("1");
        result.setData(gson.toJson(res));
        return result;
    }

    @Override
    public Result returnEvaluate(int id, String message){
        Evaluate res = evaluateMapper.selectOne(
                Wrappers.<Evaluate>lambdaQuery().eq(Evaluate::getOrderid,id)
        );
        res.setMarmessage(message);
        res.setEtime(String.valueOf(new Date().getTime()));
        Integer num =  evaluateMapper.updateById(res);
        if(num > 0){
            return new Result().success();
        }
        return new Result().error("-1","error");
    }
}
