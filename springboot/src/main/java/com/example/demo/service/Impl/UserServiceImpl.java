package com.example.demo.service.Impl;
/*
 ** @autor cc
 ** @date 2022/3/23
 */

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Result register(User user){
        if (user.getSex().equals("0")){
            user.setSex("男");
        }else if(user.getSex().equals("1")){
            user.setSex("女");
        }else{
            user.setSex("未知");
        }
        Integer insertUser = userMapper.insert(user);
        if(insertUser > 0){
            return new Result().success();
        }
        return new Result().error("1","error");
    }

    @Override
    public Result getDataByOpenid(User user){
        User res = userMapper.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getOpenid,user.getOpenid()));
        if (res == null){
            return Result.error("-1","openid不存在");
        }
        return Result.success(res);
    }

    @Override
    public Result addLove(String openid, int id){
        Result result = new Result();
        User res = userMapper.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getOpenid,openid)
        );
        String mid = String.valueOf(id);
        String love = res.getLove();
        if(love == null || love.equals("")){
            love = mid;
        }else{
            String [] arr = love.split(",");
            for (int i = 0; i < arr.length; i++){
                if (arr[i].equals(mid)){
                    result.setData("已收藏");
                    result.setCode("-1");
                    return result;
                }
            }
            love = love +"," + mid;
        }

        res.setLove(love);
        userMapper.updateById(res);
        result.setData("收藏成功");
        result.setCode("1");
        return result;
    }

    @Override
    public Result deleteLove(String openid, int id){
        Result result = new Result();
        User res = userMapper.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getOpenid,openid)
        );
        String newLove = "";
        String mid = String.valueOf(id);
        String love = res.getLove();
        String [] arr = love.split(",");
        if (arr.length > 1){
            for (int i = 0; i < arr.length; i++){
                if(arr[i].equals(mid)){
                    newLove = newLove + arr[i] + " ";
                }
            }
            newLove = newLove.trim();
            newLove.replace(" ",",");
        }
        res.setLove(newLove);
        userMapper.updateById(res);
        result.setData("删除成功");
        result.setCode("1");
        return result;
    }


    @Override
    public Result getLove(String openid){
        Result result = new Result();
        User res = userMapper.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getOpenid,openid)
        );
        String love = res.getLove();
        if(love == null){
            result.setData("");
        }else {
            result.setData(love);
        }
        return result;
    }
}
