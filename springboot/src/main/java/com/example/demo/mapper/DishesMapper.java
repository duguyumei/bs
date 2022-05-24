package com.example.demo.mapper;
/*
 ** @autor cc
 ** @date 2022/3/20
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Dishes;
import com.example.demo.entity.Marchant;
import org.springframework.stereotype.Repository;

@Repository
public interface DishesMapper extends BaseMapper<Dishes> {
}


