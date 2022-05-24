package com.example.demo.entity;
/*
 ** @autor cc
 ** @date 2022/3/22
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user") //引用了mybatis-plus插件后需要这个注释,参数值与存储该对象的表名相同
@Data//使用lombok后简化了javabean操作,不需要再写get/set方法,会自动的通过注解生成
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id; //id
    private String nikename; //昵称
    private String password; //密码
    private String username; //账号名
    private String openid;//微信openid
    private String sex; //密码
    private String phone;
    private String love;
}
