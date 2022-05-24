package com.example.demo.mapper;
/*
 ** @autor cc
 ** @date 2022/3/27
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
