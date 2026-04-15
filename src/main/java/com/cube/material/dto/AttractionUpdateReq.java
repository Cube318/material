package com.cube.material.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AttractionUpdateReq {

    @NotNull(message = "id 不能为空")
    @Min(value = 1, message = "id 必须大于等于 1")
    private Long id;

    @Size(max = 255, message = "poiObjId 长度不能超过 255")
    private String poiObjId;

    @Size(max = 255, message = "景点名称长度不能超过 255")
    private String name;

    @Size(max = 255, message = "标题长度不能超过 255")
    private String title;

    @Min(value = 0, message = "景点等级最小为 0")
    @Max(value = 3, message = "景点等级最大为 3")
    private Integer grade;

    @Size(max = 1024, message = "地址长度不能超过 1024")
    private String address;
}
