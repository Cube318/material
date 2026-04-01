package com.cube.material.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.material.entity.Attraction;
import com.cube.material.vo.AttractionDetailVO;
import com.cube.material.vo.AttractionListVO;

/**
 * @author cube
 */
public interface AttractionService extends IService<Attraction> {
    AttractionDetailVO detail(Long id);

    Page<AttractionListVO> pageWithPoi(Integer page, Integer size, String name);
}