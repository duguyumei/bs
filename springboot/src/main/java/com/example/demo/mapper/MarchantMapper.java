package com.example.demo.mapper;
/*
 ** @autor cc
 ** @date 2022/3/12
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Marchant;
import org.springframework.stereotype.Repository;


//继承mybatis-plus中的BaseMapper
@Repository
public interface MarchantMapper extends BaseMapper<Marchant> {
}
