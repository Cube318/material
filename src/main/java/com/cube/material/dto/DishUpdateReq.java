package com.cube.material.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DishUpdateReq {

    @NotNull(message = "id 不能为空")
    @Min(value = 1, message = "id 必须大于等于 1")
    private Integer id;

    private Long poiId;

    @Size(max = 255, message = "菜品名称长度不能超过 255")
    private String name;

    @Size(max = 255, message = "副标题长度不能超过 255")
    private String title;

    @Min(value = 0, message = "价格不能小于 0")
    private Integer price;

    @Size(max = 255, message = "价值标签长度不能超过 255")
    private String valueDesc;

    @Size(max = 768, message = "菜品图片长度不能超过 768")
    private String imageUrl;

    @Min(value = 0, message = "优惠价格不能小于 0")
    private Integer promoPrice;

    @Size(max = 255, message = "优惠主题色长度不能超过 255")
    private String themeBg;

    @Size(max = 255, message = "起播图长度不能超过 255")
    private String coverImageUrl;

    @Size(max = 255, message = "视频链接长度不能超过 255")
    private String videoUrl;

    private Integer orderSort;

    @Size(max = 255, message = "优惠链接长度不能超过 255")
    private String link;

    private String description;
}
