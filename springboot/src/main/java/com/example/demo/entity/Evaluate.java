package com.example.demo.entity;
/*
 ** @autor cc
 ** @date 2022/4/4
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("evaluate") //引用了mybatis-plus插件后需要这个注释,参数值与存储该对象的表名相同
@Data//使用lombok后简化了javabean操作,不需要再写get/set方法,会自动的通过注解生成
public class Evaluate {
    @TableId(value = "id",type = IdType.AUTO)//设置表id,实现自增,value值为数据库中的id字段名
    private Integer id; //id
    private Integer orderid; //订单id
    private String openid; //用户
    private Integer marchant; //商家
    private Integer source; //评分
    private String message; //信息
    private String stime; //时间
    private String url;//图片
    private String etime; //商家回复时间
    private String marmessage;
}
