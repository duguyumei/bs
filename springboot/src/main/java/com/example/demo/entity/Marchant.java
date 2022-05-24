package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/*
 ** @autor cc
 ** @date 2022/3/12
 */
@TableName("marchant") //引用了mybatis-plus插件后需要这个注释,参数值与存储该对象的表名相同
@Data//使用lombok后简化了javabean操作,不需要再写get/set方法,会自动的通过注解生成
public class Marchant {
    @TableId(value = "id",type = IdType.AUTO)//设置表id,实现自增,value值为数据库中的id字段名
    private Integer id; //商家id
    private String name; //店名
    private String password; //密码
    private String username; //账号名
    private String address; //地址
    private String message; //简介
    private String documents; //证书
    private String phone;//手机号
//    private String sales; //销量
    private Double score;//评价
    private String  url;//图片地址
    private Integer status;//状态
    private Integer area;//配送范围
    private String latitude;
    private String longitude;
}
