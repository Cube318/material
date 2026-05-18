package com.cube.material.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * Excel导出请求对象
 * 
 * @author Claude
 * @date 2026-05-18
 */
@Data
public class ExportReq {
    
    /**
     * 表名（必填）
     */
    @NotBlank(message = "表名不能为空")
    private String tableName;
    
    /**
     * 导出字段列表（必填，前端只传字段key）
     */
    @NotEmpty(message = "导出字段不能为空")
    private List<String> fields;
    
    /**
     * 筛选条件（可选）
     * key: 字段名, value: 字段值
     * 示例: {"name": "测试景点", "grade": 1}
     */
    private java.util.Map<String, Object> filters;
}
