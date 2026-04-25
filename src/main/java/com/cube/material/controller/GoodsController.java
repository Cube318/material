package com.cube.material.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cube.material.common.RetInfo;
import com.cube.material.dto.GoodsCreateReq;
import com.cube.material.dto.GoodsUpdateReq;
import com.cube.material.entity.Goods;
import com.cube.material.service.GoodsService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author cube
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 新增
     */
    @PostMapping
    public RetInfo<?> add(@Valid @RequestBody GoodsCreateReq req) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(req, goods);
        boolean saved = goodsService.save(goods);
        return saved ? RetInfo.ok() : RetInfo.error("新增失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public RetInfo<?> delete(@PathVariable @Min(value = 1, message = "id 必须大于等于 1") Integer id) {
        boolean removed = goodsService.removeById(id);
        return removed ? RetInfo.ok() : RetInfo.error("删除失败或数据不存在");
    }

    /**
     * 修改
     */
    @PutMapping
    public RetInfo<?> update(@Valid @RequestBody GoodsUpdateReq req) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(req, goods);
        boolean updated = goodsService.updateById(goods);
        return updated ? RetInfo.ok() : RetInfo.error("修改失败或数据不存在");
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public RetInfo<?> detail(@PathVariable @Min(value = 1, message = "id 必须大于等于 1") Integer id) {
        Goods goods = goodsService.getById(id);
        if (goods == null) {
            return RetInfo.error("商品不存在");
        }
        return RetInfo.ok(goods);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public RetInfo<Page<Goods>> page(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码必须大于等于 1") Integer page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页数量必须大于等于 1") @Max(value = 100, message = "每页数量必须小于等于 100") Integer size,
            String name,
            Integer goodsType) {
        return RetInfo.ok(goodsService.goodsCardList(page, size, name, goodsType));
    }
}