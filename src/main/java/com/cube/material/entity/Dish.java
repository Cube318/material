package com.cube.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("t_dish")
@Data
public class Dish {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("poiId")
    private Long poiId;

    private String name;

    private String title;

    private Integer price;

    @TableField("valueDesc")
    private String valueDesc;

    @TableField("promoPrice")
    private Integer promoPrice;

    @TableField("imageUrl")
    private String imageUrl;

    @TableField("themebg")
    private String themeBg;

    @TableField("coverImageUrl")
    private String coverImageUrl;

    @TableField("videoUrl")
    private String videoUrl;

    @TableField("orderSort")
    private Integer orderSort;

    private String link;

    private String description;

    @TableField("createBy")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateBy")
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("updateTime")
    private LocalDateTime updateTime;

    private String info;
}
