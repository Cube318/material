package com.cube.material.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cube.material.common.RetInfo;
import com.cube.material.entity.Attraction;
import com.cube.material.service.AttractionService;
import com.cube.material.vo.AttractionDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @author cube
 */
@Slf4j
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
        boolean saved = attractionService.save(attraction);
        return saved ? RetInfo.ok() : RetInfo.error("新增失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public RetInfo<?> delete(@PathVariable Long id) {
        boolean removed = attractionService.removeById(id);
        return removed ? RetInfo.ok() : RetInfo.error("删除失败或数据不存在");
    }

    /**
     * 修改
     */
    @PutMapping
    public RetInfo<?> update(@RequestBody Attraction attraction) {
        boolean updated = attractionService.updateById(attraction);
        return updated ? RetInfo.ok() : RetInfo.error("修改失败或数据不存在");
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public RetInfo<?> detail(@PathVariable Long id) {
        log.info("========== [Controller] 请求景点详情，id = " + id + " ==========");

        AttractionDetailVO detailVO = attractionService.detail(id);

        if (detailVO == null) {
            return RetInfo.error("景点不存在");
        }

        List<AttractionDetailVO.VideoVO> videos = detailVO.getVideos();

        log.info("========== [Controller] 查询完成，poi.objectId = "
                + (detailVO.getPoi() != null ? detailVO.getPoi().getObjectId() : "NULL")
                + "，视频数量 = " + (videos != null ? videos.size() : 0) + " ==========");

        return RetInfo.ok(detailVO);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public RetInfo<?> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            String name,
            Integer grade
    ) {
        if (page == null || page < 1) {
            return RetInfo.error("页码必须大于等于 1");
        }
        if (size == null || size < 1 || size > 100) {
            return RetInfo.error("每页数量必须在 1 到 100 之间");
        }

        Page<Attraction> p = new Page<>(page, size);

        QueryWrapper<Attraction> qw = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            qw.like("name", name);
        }

        if (grade != null) {
            qw.eq("grade", grade);
        }

        return RetInfo.ok(attractionService.page(p, qw));
    }
}