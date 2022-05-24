package com.example.demo.service;
/*
 ** @autor cc
 ** @date 2022/3/23
 */

import com.example.demo.common.Result;
import com.example.demo.entity.User;

public interface IUserService {
    public Result register(User user);
    public Result getDataByOpenid(User user);
    public Result addLove(String openid, int id);
    public Result deleteLove(String openid, int id);
    public Result getLove(String openid);
}
