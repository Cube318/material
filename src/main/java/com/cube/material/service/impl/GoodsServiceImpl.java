package com.cube.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.material.entity.Goods;
import com.cube.material.mapper.GoodsMapper;
import com.cube.material.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author cube
 */
@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public Page<Goods> goodsCardList(Integer page, Integer size, String name, Integer goodsType) {
        Page<Goods> p = new Page<>(page, size);

        QueryWrapper<Goods> qw = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            qw.like("name", name);
        }
        if (goodsType != null) {
            qw.eq("goodsType", goodsType);
        }
        qw.orderByAsc("orderSort");

        this.page(p, qw);
        return p;
    }
}