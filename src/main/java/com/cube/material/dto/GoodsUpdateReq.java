package com.cube.material.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author cube
 */
@Data
public class GoodsUpdateReq {

    @NotNull(message = "id 不能为空")
    @Min(value = 1, message = "id 必须大于等于 1")
    private Integer id;

    // ========== 基础信息 ==========

    @Size(max = 255, message = "商品名称不能超过 255 个字符")
    private String name;

    @Size(max = 255, message = "副标题不能超过 255 个字符")
    private String title;

    @Min(value = 1, message = "商品类型必须为 1~5")
    @Max(value = 5, message = "商品类型必须为 1~5")
    private Integer goodsType;

    @Size(max = 255, message = "价值标签不能超过 255 个字符")
    private String valueDesc;

    // ========== 价格信息 ==========

    @Min(value = 0, message = "原价不能为负数")
    private Integer price;

    @Min(value = 0, message = "优惠价不能为负数")
    private Integer promoPrice;

    // ========== 媒体信息 ==========

    @Size(max = 768, message = "封面图片链接不能超过 768 个字符")
    private String imageUrl;

    @Size(max = 255, message = "详情封面链接不能超过 255 个字符")
    private String coverImageUrl;

    @Size(max = 255, message = "视频链接不能超过 255 个字符")
    private String videoUrl;

    @Size(max = 255, message = "主题色不能超过 255 个字符")
    private String themebg;

    // ========== 业务关联 ==========

    @Min(value = 1, message = "poiId 必须大于等于 1")
    private Long poiId;

    @Size(max = 255, message = "外部商品 ID 不能超过 255 个字符")
    private String xdProductId;

    @Size(max = 255, message = "跳转链接不能超过 255 个字符")
    private String link;

    private Integer orderSort;

    private Integer isAdvertising;

    // ========== 描述信息 ==========

    private String description;

    private String info;
}