package com.cube.material.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.material.entity.Attraction;
import com.cube.material.mapper.AttractionMapper;
import com.cube.material.service.AttractionService;
import com.cube.material.vo.AttractionDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 17815
 */
@Slf4j
@Service
public class AttractionServiceImpl extends ServiceImpl<AttractionMapper, Attraction> implements AttractionService {


    @Override
    public AttractionDetailVO detail(Long id) {
        log.info("========== [Service] 调用 Mapper 查询，id = {} ==========", id);

        AttractionDetailVO vo = baseMapper.getDetailWithPoi(id);

        if (vo == null) {
            log.warn("========== [Service] 查询结果为空，id = {} ==========", id);
            return null;
        }

        String poiObjectId = (vo != null && vo.getPoi() != null && vo.getPoi().getObjectId() != null) ? vo.getPoi().getObjectId() : "NULL";

        log.info("========== [Service] 查询完成，poiObjectId = {} ==========", poiObjectId);
        return vo;
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