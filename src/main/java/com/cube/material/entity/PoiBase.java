package com.cube.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@TableName("t_poi_base")
@Data
public class PoiBase {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("objectId")
    private String objectId;

    @TableField("aoiObjId")
    private String aoiObjId;

    private String name;

    private String address;

    @TableField("reviewNum")
    private Integer reviewNum;

    @TableField("favorite")
    private Integer favorite;

    private Double rating;

    private Double longitude;

    private Double latitude;

    private String tel;

    @TableField("businessHours")
    private String businessHours;

    @TableField("categoryType")
    private Integer categoryType;

    @TableField("poiType")
    private Integer poiType;

    private String description;

    private Integer status;

    @TableField("createBy")
    private String createBy;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateBy")
    private String updateBy;

    @TableField("updateTime")
    private LocalDateTime updateTime;

    private String info;
}