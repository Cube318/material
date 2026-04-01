package com.cube.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.material.entity.Attraction;
import com.cube.material.vo.AttractionDetailVO;

public interface AttractionService extends IService<Attraction> {
    AttractionDetailVO detail(Long id);
}