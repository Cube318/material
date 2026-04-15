package com.cube.material.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.material.entity.Dish;
import com.cube.material.mapper.DishMapper;
import com.cube.material.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @author cube
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
