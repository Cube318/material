package com.cube.material.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cube.material.common.RetInfo;
import com.cube.material.dto.AttractionCreateReq;
import com.cube.material.dto.AttractionUpdateReq;
import com.cube.material.entity.Attraction;
import com.cube.material.service.AttractionService;
import com.cube.material.vo.AttractionDetailVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @author cube
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/attraction")
public class AttractionController {

    @Resource
    private AttractionService attractionService;

    /**
     * 新增
     */
    @PostMapping
    public RetInfo<?> add(@Valid @RequestBody AttractionCreateReq req) {
        Attraction attraction = new Attraction();
        BeanUtils.copyProperties(req, attraction);
        boolean saved = attractionService.save(attraction);
        return saved ? RetInfo.ok() : RetInfo.error("新增失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public RetInfo<?> delete(@PathVariable @Min(value = 1, message = "id 必须大于等于 1") Long id) {
        boolean removed = attractionService.removeById(id);
        return removed ? RetInfo.ok() : RetInfo.error("删除失败或数据不存在");
    }

    /**
     * 修改
     */
    @PutMapping
    public RetInfo<?> update(@Valid @RequestBody AttractionUpdateReq req) {
        Attraction attraction = new Attraction();
        BeanUtils.copyProperties(req, attraction);
        boolean updated = attractionService.updateById(attraction);
        return updated ? RetInfo.ok() : RetInfo.error("修改失败或数据不存在");
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public RetInfo<?> detail(@PathVariable @Min(value = 1, message = "id 必须大于等于 1") Long id) {
        log.info("========== [Controller] 请求景点详情，id = {} ==========", id);

        AttractionDetailVO detailVO = attractionService.detail(id);

        if (detailVO == null) {
            return RetInfo.error("景点不存在");
        }

        List<AttractionDetailVO.VideoVO> videos = detailVO.getVideos();

        String poiObjectId = detailVO.getPoi() != null ? detailVO.getPoi().getObjectId() : "NULL";
        int videoCount = videos != null ? videos.size() : 0;
        log.info("========== [Controller] 查询完成，poi.objectId = {}，视频数量 = {} ==========", poiObjectId, videoCount);

        return RetInfo.ok(detailVO);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public RetInfo<?> page(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码必须大于等于 1") Integer page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页数量必须大于等于 1") @Max(value = 100, message = "每页数量必须小于等于 100") Integer size,
            String name,
            Integer grade
    ) {
        Page<Attraction> p = new Page<>(page, size);

        QueryWrapper<Attraction> qw = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            qw.like("name", name);
        }

        if (grade != null) {
            qw.eq("grade", grade);
        }

        return RetInfo.ok(attractionService.page(p, qw));
    }
}