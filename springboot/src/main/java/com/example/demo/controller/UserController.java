package com.example.demo.controller;
/*
 ** @autor cc
 ** @date 2022/3/22
 */

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/customer")
public class UserController {
    @Resource
    IUserService userService;

    //获取唯一标识
    @PostMapping("/getOpenid")
    public Result getOpenid(@RequestBody String code) throws IOException {
        JSONObject jsonObject =  JSONUtil.parseObj(code);
        String appid = "wx9e61754d025257c2";
        String appSecret = "f877585505fb680660f935fdb2863759";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appSecret + "&js_code=" + jsonObject.get("code") + "&grant_type=authorization_code";
        //客户端
        OkHttpClient client = new OkHttpClient();
        //request对象
        Request request = new Request.Builder().url(url).build();
        //reponse
        Response response = client.newCall(request).execute();
        //请求成功
        String str;
        if (response.isSuccessful()){
            str = response.body().string();
            System.out.println(str);
        }else {
            return new Result().error("-1","没有取到");
        }
        return new Result(str);
    }

    //新增
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user){
        //根据openid查看是否注册
        Result res = userService.getDataByOpenid(user);
        Result result = new Result();
        if(res.getCode().equals("-1")){
            //数据库插入
            result = userService.register(user);
        }
        //数据库插入
        result.setCode("-1");
        result.setMsg("用户已存在");
        System.out.println(result.getMsg());
        return result;
    }


    //获取用户信息
    @PostMapping("/getDataByUsername")
    public Result<?> getDataByOpenid(@RequestBody User user){
        Result result = userService.getDataByOpenid(user);
        return result;
    }

    //新增收藏
    @GetMapping("/addLove")
    public Result<?> addlove(@RequestParam String openid, int id){
        Result result = userService.addLove(openid,id);
        return result;
    }

    //取消收藏
    @GetMapping("/deleteLove")
    public Result<?> deletelove(@RequestParam String openid, int id){
        Result result = userService.deleteLove(openid,id);
        return result;
    }

    //返回收藏
    @GetMapping("getLove")
    public Result<?> getLove(@RequestParam String openid){
        Result result = userService.getLove(openid);
        return result;
    }
}
