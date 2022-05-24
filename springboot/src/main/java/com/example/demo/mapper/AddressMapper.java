package com.example.demo.mapper;
/*
 ** @autor cc
 ** @date 2022/3/25
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressMapper extends BaseMapper<Address> {
}
