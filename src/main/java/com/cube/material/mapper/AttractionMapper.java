package com.cube.material.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cube.material.entity.Attraction;
import com.cube.material.vo.AttractionDetailVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
/**
 * @author cube
 */
public interface AttractionMapper extends BaseMapper<Attraction> {
    // 可以自定义额外方法
    /**
     * 查询景点详情 + POI 基础信息
     */


    @Select("""
    SELECT 
        a.*,
        p.objectId,
        p.name              AS poi_name,
        p.address           AS poi_address,
        p.longitude         AS poi_longitude,
        p.latitude          AS poi_latitude,
        p.tel               AS poi_tel,
        p.businessHours     AS poi_businessHours,
        p.categoryType      AS poi_categoryType,
        p.poiType           AS poi_poiType,
        p.description       AS poi_description,
        p.status            AS poi_status,
        p.rating            AS poi_rating,
        p.reviewNum         AS poi_reviewNum,
        p.favorite          AS poi_favorite
    FROM t_attraction a
    LEFT JOIN t_poi_base p ON a.poiObjId = p.objectId
    WHERE a.id = #{id}
    """)
    @Results({
            @Result(property = "poi.objectId",       column = "objectId"),
            @Result(property = "poi.name",           column = "poi_name"),
            @Result(property = "poi.address",        column = "poi_address"),
            @Result(property = "poi.longitude",      column = "poi_longitude"),
            @Result(property = "poi.latitude",       column = "poi_latitude"),
            @Result(property = "poi.tel",            column = "poi_tel"),
            @Result(property = "poi.businessHours",  column = "poi_businessHours"),
            @Result(property = "poi.categoryType",   column = "poi_categoryType"),
            @Result(property = "poi.poiType",        column = "poi_poiType"),
            @Result(property = "poi.description",    column = "poi_description"),
            @Result(property = "poi.status",         column = "poi_status"),
            @Result(property = "poi.rating",         column = "poi_rating"),
            @Result(property = "poi.reviewNum",      column = "poi_reviewNum"),
            @Result(property = "poi.favorite",       column = "poi_favorite")
    })
    AttractionDetailVO getDetailWithPoi(Long id);

}