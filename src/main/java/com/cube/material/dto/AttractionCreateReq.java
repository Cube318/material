package com.cube.material.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AttractionCreateReq {

    @NotBlank(message = "poiObjId 不能为空")
    @Size(max = 255, message = "poiObjId 长度不能超过 255")
    private String poiObjId;

    @NotBlank(message = "景点名称不能为空")
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
