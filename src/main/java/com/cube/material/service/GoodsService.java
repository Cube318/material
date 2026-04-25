package com.cube.material.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.material.entity.Goods;

/**
 * @author cube
 */
public interface GoodsService extends IService<Goods> {

    Page<Goods> goodsCardList(Integer page, Integer size, String name, Integer goodsType);
}
