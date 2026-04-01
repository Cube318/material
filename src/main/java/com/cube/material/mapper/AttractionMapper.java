package com.cube.material.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cube.material.entity.Attraction;
import com.cube.material.vo.AttractionDetailVO;
import com.cube.material.vo.AttractionListVO;
import org.apache.ibatis.annotations.Param;
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


    /**
     * 分页查询景点列表 + POI 基础信息
     */
    @Select("""
        <script>
        SELECT 
            a.id,
            a.poiObjId,
            a.name,
            a.title,
            a.scenicRating,
            a.grade,
            a.recPriority,
            a.valueDesc,
            a.locationArea,
            a.advantage,
            a.reviewNum,
            a.favorite,
            a.rating,
            a.imageUrl,
            a.guideMapUrl,
            a.guideMapFlag,
            a.routeIconUrl,
            a.summaryVideoUrl,
            a.ticketPrice,
            a.introduce,
            a.introduceAudio,
            a.explanation,
            a.explanationUrl,
            a.featuresType,
            a.attractionType,
            a.scale,
            a.serviceType,
            a.createBy,
            a.createTime,
            a.updateBy,
            a.updateTime,
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
        <where>
            <if test="name != null and name != ''">
                a.name LIKE CONCAT('%', #{name}, '%')
            </if>
        </where>
        ORDER BY a.id ASC
        </script>
        """)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "poiObjId", column = "poiObjId"),
            @Result(property = "name", column = "name"),
            @Result(property = "title", column = "title"),
            @Result(property = "scenicRating", column = "scenicRating"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "recPriority", column = "recPriority"),
            @Result(property = "valueDesc", column = "valueDesc"),
            @Result(property = "locationArea", column = "locationArea"),
            @Result(property = "advantage", column = "advantage"),
            @Result(property = "reviewNum", column = "reviewNum"),
            @Result(property = "favorite", column = "favorite"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "imageUrl", column = "imageUrl"),
            @Result(property = "guideMapUrl", column = "guideMapUrl"),
            @Result(property = "guideMapFlag", column = "guideMapFlag"),
            @Result(property = "routeIconUrl", column = "routeIconUrl"),
            @Result(property = "summaryVideoUrl", column = "summaryVideoUrl"),
            @Result(property = "ticketPrice", column = "ticketPrice"),
            @Result(property = "introduce", column = "introduce"),
            @Result(property = "introduceAudio", column = "introduceAudio"),
            @Result(property = "explanation", column = "explanation"),
            @Result(property = "explanationUrl", column = "explanationUrl"),
            @Result(property = "featuresType", column = "featuresType"),
            @Result(property = "attractionType", column = "attractionType"),
            @Result(property = "scale", column = "scale"),
            @Result(property = "serviceType", column = "serviceType"),
            @Result(property = "createBy", column = "createBy"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateBy", column = "updateBy"),
            @Result(property = "updateTime", column = "updateTime"),
            // POI 嵌套映射
            @Result(property = "poi.objectId", column = "objectId"),
            @Result(property = "poi.name", column = "poi_name"),
            @Result(property = "poi.address", column = "poi_address"),
            @Result(property = "poi.longitude", column = "poi_longitude"),
            @Result(property = "poi.latitude", column = "poi_latitude"),
            @Result(property = "poi.tel", column = "poi_tel"),
            @Result(property = "poi.businessHours", column = "poi_businessHours"),
            @Result(property = "poi.categoryType", column = "poi_categoryType"),
            @Result(property = "poi.poiType", column = "poi_poiType"),
            @Result(property = "poi.description", column = "poi_description"),
            @Result(property = "poi.status", column = "poi_status"),
            @Result(property = "poi.rating", column = "poi_rating"),
            @Result(property = "poi.reviewNum", column = "poi_reviewNum"),
            @Result(property = "poi.favorite", column = "poi_favorite")
    })
    Page<AttractionListVO> pageWithPoi(Page<AttractionListVO> page, @Param("name") String name);
}
