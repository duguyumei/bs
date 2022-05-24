package com.example.demo.entity;
/*
 ** @autor cc
 ** @date 2022/3/25
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("address") //引用了mybatis-plus插件后需要这个注释,参数值与存储该对象的表名相同
@Data//使用lombok后简化了javabean操作,不需要再写get/set方法,会自动的通过注解生成
public class Address {
    @TableId(value = "id",type = IdType.AUTO)//设置表id,实现自增,value值为数据库中的id字段名
    private Integer id; //id
    private String name; //用户名
    private String openid;//用户id
    private String addressname; //地址名
    private String address; //地址信息
    private String message; //详细地址
    private String latitude; //经度
    private String longitude; //维度
    private String phone; //手机
    private String time; //修改时间
    private String sex; //称呼
}
