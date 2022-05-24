package com.example.demo.entity;
/*
 ** @autor cc
 ** @date 2022/3/27
 */


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("orders") //引用了mybatis-plus插件后需要这个注释,参数值与存储该对象的表名相同
@Data//使用lombok后简化了javabean操作,不需要再写get/set方法,会自动的通过注解生成
public class Order {
    @TableId(value = "id",type = IdType.AUTO)//设置表id,实现自增,value值为数据库中的id字段名
    private Integer id; //订单id
    private int marchant; //餐厅
    private String openid; //openid
    private String dish; //菜
    private String ride; //骑手
    private Double money; //金额
    private String pay; //送餐点
    private String address; //地址
    private String stime; //开始时间
    private String etime; //结束时间
    private String evaluate; //评分
    private String state; //状态
    private String people; //收货人
    private String message;//备注
    private String url;
}
