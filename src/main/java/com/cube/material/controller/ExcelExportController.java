package com.cube.material.controller;

import com.cube.material.common.RetInfo;
import com.cube.material.dto.ExportReq;
import com.cube.material.enums.ExportFieldEnum;
import com.cube.material.enums.ExportTableEnum;
import com.cube.material.enums.ExportTemplateEnum;
import com.cube.material.service.ExcelExportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Excel导出Controller
 * 
 * @author Claude
 * @date 2026-05-18
 */
@Slf4j
@RestController
@RequestMapping("/export")
public class ExcelExportController {
    
    @Autowired
    private ExcelExportService excelExportService;
    
    /**
     * 获取所有支持导出的表列表
     * 
     * @return 表列表
     */
    @GetMapping("/tables")
    public RetInfo<List<Map<String, String>>> getTables() {
        List<Map<String, String>> tables = new ArrayList<>();
        
        for (ExportTableEnum table : ExportTableEnum.values()) {
            Map<String, String> tableInfo = new HashMap<>();
            tableInfo.put("tableName", table.getTableName());
            tableInfo.put("tableDesc", table.getTableDesc());
            tableInfo.put("datasource", table.getDatasource());
            tables.add(tableInfo);
        }
        
        return RetInfo.ok(tables);
    }
    
    /**
     * 获取指定表的可导出字段列表
     * 
     * @param tableName 表名
     * @return 字段列表
     */
    @GetMapping("/fields/{tableName}")
    public RetInfo<List<Map<String, String>>> getFields(@PathVariable String tableName) {
        // 校验表名
        ExportTableEnum tableEnum = ExportTableEnum.getByTableName(tableName);
        if (tableEnum == null) {
            return RetInfo.error("不支持导出的表: " + tableName);
        }
        
        // 获取字段映射
        Map<String, String> fieldMap = ExportFieldEnum.getFieldMapByTable(tableName);
        
        List<Map<String, String>> fields = new ArrayList<>();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            Map<String, String> fieldInfo = new HashMap<>();
            fieldInfo.put("fieldKey", entry.getKey());
            fieldInfo.put("fieldName", entry.getValue());
            fields.add(fieldInfo);
        }
        
        return RetInfo.ok(fields);
    }
    
    /**
     * 导出Excel
     * 
     * @param req 导出请求参数
     * @param response HTTP响应对象
     */
    @PostMapping("/excel")
    public void exportExcel(@Valid @RequestBody ExportReq req, HttpServletResponse response) {
        try {
            log.info("收到导出请求: {}", req);
            excelExportService.exportExcel(req, response);
        } catch (Exception e) {
            log.error("导出失败", e);
            // 由于已经写入了response，这里无法再返回JSON，只能记录日志
            // 前端需要通过blob响应的处理来识别错误
            throw new RuntimeException("导出失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 获取所有表及其字段信息（用于前端一次性加载）
     * 
     * @return 所有表及字段信息
     */
    @GetMapping("/metadata")
    public RetInfo<Map<String, Object>> getMetadata() {
        Map<String, Object> metadata = new HashMap<>();
        
        // 获取所有表
        List<Map<String, String>> tables = new ArrayList<>();
        for (ExportTableEnum table : ExportTableEnum.values()) {
            Map<String, String> tableInfo = new HashMap<>();
            tableInfo.put("tableName", table.getTableName());
            tableInfo.put("tableDesc", table.getTableDesc());
            tableInfo.put("datasource", table.getDatasource());
            tables.add(tableInfo);
        }
        metadata.put("tables", tables);
        
        // 获取所有字段
        Map<String, List<Map<String, String>>> allFields = new HashMap<>();
        for (ExportTableEnum table : ExportTableEnum.values()) {
            String tableName = table.getTableName();
            Map<String, String> fieldMap = ExportFieldEnum.getFieldMapByTable(tableName);
            
            List<Map<String, String>> fields = new ArrayList<>();
            for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
                Map<String, String> fieldInfo = new HashMap<>();
                fieldInfo.put("fieldKey", entry.getKey());
                fieldInfo.put("fieldName", entry.getValue());
                fields.add(fieldInfo);
            }
            allFields.put(tableName, fields);
        }
        metadata.put("fields", allFields);
        
        return RetInfo.ok(metadata);
    }
    
    /**
     * 获取所有导出模板列表
     * 
     * @return 模板列表
     */
    @GetMapping("/templates")
    public RetInfo<List<Map<String, String>>> getTemplates() {
        List<Map<String, String>> templates = new ArrayList<>();
        
        for (ExportTemplateEnum template : ExportTemplateEnum.values()) {
            Map<String, String> templateInfo = new HashMap<>();
            templateInfo.put("templateKey", template.getTemplateKey());
            templateInfo.put("templateName", template.getTemplateName());
            templateInfo.put("templateDesc", template.getTemplateDesc());
            templateInfo.put("datasource", template.getDatasource());
            templates.add(templateInfo);
        }
        
        return RetInfo.ok(templates);
    }
    
    /**
     * 使用模板导出Excel
     * 
     * @param templateKey 模板Key
     * @param response HTTP响应对象
     */
    @GetMapping("/template/{templateKey}")
    public void exportByTemplate(@PathVariable String templateKey, HttpServletResponse response) {
        try {
            log.info("收到模板导出请求, 模板Key: {}", templateKey);
            excelExportService.exportByTemplate(templateKey, response);
        } catch (Exception e) {
            log.error("模板导出失败", e);
            throw new RuntimeException("模板导出失败: " + e.getMessage(), e);
        }
    }
}
