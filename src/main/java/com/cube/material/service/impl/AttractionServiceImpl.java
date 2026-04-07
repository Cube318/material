package com.cube.material.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.material.entity.Attraction;
import com.cube.material.mapper.AttractionMapper;
import com.cube.material.service.AttractionService;
import com.cube.material.vo.AttractionDetailVO;
import com.cube.material.vo.AttractionListVO;
import org.springframework.stereotype.Service;

/**
 * @author 17815
 */
@Service
public class AttractionServiceImpl extends ServiceImpl<AttractionMapper, Attraction>
        implements AttractionService {

    @Override
    public AttractionDetailVO detail(Long id) {
        System.out.println("========== [Service] 调用 Mapper 查询，id = " + id + " ==========");

        AttractionDetailVO vo = baseMapper.getDetailWithPoi(id);

        System.out.println("========== [Service] 查询完成，poiObjectId = " +
                (vo != null && vo.getPoi() != null && vo.getPoi().getObjectId() != null
                        ? vo.getPoi().getObjectId()
                        : "NULL") + " ==========");

        if (vo == null) {
            throw new RuntimeException("景点不存在");
        }
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