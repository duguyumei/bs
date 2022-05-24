package com.example.demo.entity;
/*
 ** @autor cc
 ** @date 2022/3/20
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("dishes") //引用了mybatis-plus插件后需要这个注释,参数值与存储该对象的表名相同
@Data//使用lombok后简化了javabean操作,不需要再写get/set方法,会自动的通过注解生成
public class Dishes {
    @TableId(value = "id",type = IdType.AUTO)//设置表id,实现自增,value值为数据库中的id字段名
    private Integer id; //菜品id
    private String name; //菜品名
//    private String sales; //销量
//    private String praise; //赞
    private String message; //信息
    private double money; // 价格
//    private double discount; //折扣
    private String url; //图片
//    private String gather; //分类
    private String state; //状态
//    private int monthsales; // 月销量
    private int marchant; //商家
}
