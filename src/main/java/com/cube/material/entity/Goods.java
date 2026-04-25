package com.cube.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cube
 */
@Data
@TableName("t_goods")
public class Goods {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("poiId")
    private Long poiId;

    private String name;

    private String title;

    @TableField("xdProductId")
    private String xdProductId;

    private Integer price;

    @TableField("goodsType")
    private Integer goodsType;

    @TableField("valueDesc")
    private String valueDesc;

    @TableField("promoPrice")
    private Integer promoPrice;

    @TableField("imageUrl")
    private String imageUrl;

    @TableField("themebg")
    private String themebg;

    @TableField("coverImageUrl")
    private String coverImageUrl;

    @TableField("videoUrl")
    private String videoUrl;

    @TableField("orderSort")
    private Integer orderSort;

    @TableField("link")
    private String link;

    @TableField("description")
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
    @TableField("info")
    private String info;

    @TableField("tenantId")
    private String tenantId;

    @TableField("isAdvertising")
    private Integer isAdvertising;
}