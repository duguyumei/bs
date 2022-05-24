package com.example.demo.mapper;
/*
 ** @autor cc
 ** @date 2022/3/23
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
