package com.cube.material.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.material.entity.Attraction;
import com.cube.material.vo.AttractionDetailVO;
import com.cube.material.vo.AttractionVideoVO;

import java.util.List;

/**
 * @author cube
 */
public interface AttractionService extends IService<Attraction> {
    AttractionDetailVO detail(Long id);

    Page<Attraction> attractionCardList(Integer page, Integer size, String name, Integer grade);

    List<AttractionVideoVO> getAllSecondaryAttractions();

//    Page<AttractionListVO> pageWithPoi(Integer page, Integer size, String name);
}