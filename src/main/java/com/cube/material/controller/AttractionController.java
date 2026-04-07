package com.cube.material.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cube.material.common.RetInfo;
import com.cube.material.entity.Attraction;
import com.cube.material.service.AttractionService;
import com.cube.material.vo.AttractionDetailVO;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/attraction")
public class AttractionController {

    @Resource
    private AttractionService attractionService;

    /**
     * 新增
     */
    @PostMapping
    public RetInfo<?> add(@RequestBody Attraction attraction) {
        attractionService.save(attraction);
        return RetInfo.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public RetInfo<?> delete(@PathVariable Long id) {
        attractionService.removeById(id);
        return RetInfo.ok();
    }

    /**
     * 修改
     */
    @PutMapping
    public RetInfo<?> update(@RequestBody Attraction attraction) {
        attractionService.updateById(attraction);
        return RetInfo.ok();
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public RetInfo<?> detail(@PathVariable Long id) {
        System.out.println("========== [Controller] 请求景点详情，id = " + id + " ==========");

        AttractionDetailVO detailVO = attractionService.detail(id);

        // 适配嵌套结构后的打印
        String poiObjectId = (detailVO.getPoi() != null && detailVO.getPoi().getObjectId() != null)
                ? detailVO.getPoi().getObjectId()
                : "NULL";

        System.out.println("========== [Controller] 返回数据完成，poi.objectId = " + poiObjectId + " ==========");

        return RetInfo.ok(detailVO);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public RetInfo<?> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            String name
    ) {

        Page<Attraction> p = new Page<>(page, size);

        QueryWrapper<Attraction> qw = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            qw.like("name", name);
        }

        return RetInfo.ok(attractionService.page(p, qw));
    }
}