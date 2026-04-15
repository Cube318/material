package com.cube.material.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cube.material.common.RetInfo;
import com.cube.material.dto.DishCreateReq;
import com.cube.material.dto.DishUpdateReq;
import com.cube.material.entity.Dish;
import com.cube.material.service.DishService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author cube
 */
@Validated
@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    /**
     * 新增
     */
    @PostMapping
    public RetInfo<?> add(@Valid @RequestBody DishCreateReq req) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(req, dish);
        boolean saved = dishService.save(dish);
        return saved ? RetInfo.ok() : RetInfo.error("新增失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public RetInfo<?> delete(@PathVariable @Min(value = 1, message = "id 必须大于等于 1") Integer id) {
        boolean removed = dishService.removeById(id);
        return removed ? RetInfo.ok() : RetInfo.error("删除失败或数据不存在");
    }

    /**
     * 修改
     */
    @PutMapping
    public RetInfo<?> update(@Valid @RequestBody DishUpdateReq req) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(req, dish);
        boolean updated = dishService.updateById(dish);
        return updated ? RetInfo.ok() : RetInfo.error("修改失败或数据不存在");
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public RetInfo<?> detail(@PathVariable @Min(value = 1, message = "id 必须大于等于 1") Integer id) {
        Dish dish = dishService.getById(id);
        if (dish == null) {
            return RetInfo.error("食品卡不存在");
        }
        return RetInfo.ok(dish);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public RetInfo<?> page(
            @RequestParam(defaultValue = "1") 
            @Min(value = 1, message = "页码必须大于等于 1") Integer page,
            
            @RequestParam(defaultValue = "10") 
            @Min(value = 1, message = "每页数量必须大于等于 1")
            @Max(value = 100, message = "每页数量必须小于等于 100") Integer size,
            String name
    ) {
        Page<Dish> p = new Page<>(page, size);

        QueryWrapper<Dish> qw = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            qw.like("name", name);
        }

        return RetInfo.ok(dishService.page(p, qw));
    }
}
