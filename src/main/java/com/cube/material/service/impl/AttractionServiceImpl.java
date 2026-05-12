package com.cube.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.material.entity.Attraction;
import com.cube.material.mapper.AttractionMapper;
import com.cube.material.service.AttractionService;
import com.cube.material.vo.AttractionDetailVO;
import com.cube.material.vo.AttractionVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 17815
 */
@Slf4j
@Service
public class AttractionServiceImpl extends ServiceImpl<AttractionMapper, Attraction> implements AttractionService {

    @Autowired
    private AttractionMapper attractionMapper;

    @Override
    public AttractionDetailVO detail(Long id) {

        AttractionDetailVO vo = baseMapper.getDetailWithPoi(id);

        if (vo == null) {
            log.warn("========== [Service] 查询结果为空，id = {} ==========", id);
            return null;
        }
        String poiObjectId = (vo.getPoi() != null && vo.getPoi().getObjectId() != null) ? vo.getPoi().getObjectId() : "NULL";

        log.info("========== [Service] 查询完成，poiObjectId = {} ==========", poiObjectId);
        return vo;
    }

    @Override
    public Page<Attraction> attractionCardList(Integer page, Integer size, String name , Integer grade) {
        Page<Attraction> p = new Page<>(page, size);

        QueryWrapper<Attraction> qw = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            qw.like("name", name);
        }

        if (grade != null) {
            qw.eq("grade", grade);
        }
        this.page(p, qw);

        return p;
    }

    @Override
    public List<AttractionVideoVO> getAllSecondaryAttractions() {
        return attractionMapper.getSecondaryAttractionsWithVideos();
    }

//    /**
//     * 分页查询景点列表（包含 POI 信息）
//     */
//    @Override
//    public Page<AttractionListVO> pageWithPoi(Integer page, Integer size, String name) {
//        Page<AttractionListVO> pageParam = new Page<>(page, size);
//        return baseMapper.pageWithPoi(pageParam, name);
//    }
}