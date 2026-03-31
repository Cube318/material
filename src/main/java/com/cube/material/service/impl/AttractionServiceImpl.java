package com.cube.material.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.material.entity.Attraction;
import com.cube.material.mapper.AttractionMapper;
import com.cube.material.service.AttractionService;
import org.springframework.stereotype.Service;

/**
 * @author cube
 */
@Service
public class AttractionServiceImpl
        extends ServiceImpl<AttractionMapper, Attraction>
        implements AttractionService {
    // 自定义方法
}