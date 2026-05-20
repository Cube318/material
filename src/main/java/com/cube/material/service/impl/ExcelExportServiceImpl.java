package com.cube.material.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.cube.material.dto.ExportReq;
import com.cube.material.enums.ExportFieldEnum;
import com.cube.material.enums.ExportTableEnum;
import com.cube.material.enums.ExportTemplateEnum;
import com.cube.material.handler.CustomColumnWidthStyleStrategy;
import com.cube.material.service.ExcelExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Excel导出Service实现类
 * 
 * @author cube
 * @date 2026-05-18
 */
@Slf4j
@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 每页查询数量（分页查询，避免OOM）
     */
    private static final int PAGE_SIZE = 1000;
    
    /**
     * 日期时间格式化器
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void exportExcel(ExportReq req, HttpServletResponse response) {
        log.info("开始导出Excel，表名: {}, 字段: {}", req.getTableName(), req.getFields());
        
        // 1. 校验表名
        ExportTableEnum tableEnum = ExportTableEnum.getByTableName(req.getTableName());
        if (tableEnum == null) {
            throw new RuntimeException("不支持导出的表: " + req.getTableName());
        }
        
        // 2. 校验字段白名单
        for (String field : req.getFields()) {
            if (!ExportFieldEnum.isValidField(req.getTableName(), field)) {
                throw new RuntimeException("非法字段: " + field);
            }
        }
        
        // 3. 设置响应头
        String fileName = generateFileName(req.getTableName());
        setResponseHeader(response, fileName);
        
        // 4. 执行导出
        try {
            doExport(req, response, tableEnum);
            log.info("导出Excel成功，表名: {}, 文件名: {}", req.getTableName(), fileName);
        } catch (Exception e) {
            log.error("导出Excel失败，表名: {}", req.getTableName(), e);
            throw new RuntimeException("导出失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 执行导出（使用分页查询 + 流式写入）
     */
    private void doExport(ExportReq req, HttpServletResponse response, ExportTableEnum tableEnum) throws IOException {
        // 构建中文表头
        List<List<String>> headers = buildHeaders(req.getTableName(), req.getFields());
        
        // 配置Excel样式
        HorizontalCellStyleStrategy styleStrategy = createCellStyleStrategy();
        
        // 配置列宽自适应策略
        CustomColumnWidthStyleStrategy columnWidthStyleStrategy = new CustomColumnWidthStyleStrategy();
        
        // 使用EasyExcel流式写入
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .head(headers)
                .registerWriteHandler(styleStrategy)
                .registerWriteHandler(columnWidthStyleStrategy)
                .build()) {
            
            WriteSheet writeSheet = EasyExcel.writerSheet("数据").build();
            
            // 分页查询并写入
            int pageNum = 1;
            int totalCount = 0;
            
            while (true) {
                // 根据数据源切换查询
                List<Map<String, Object>> pageData = queryPageData(
                        tableEnum.getDatasource(),
                        req.getTableName(),
                        req.getFields(),
                        req.getFilters(),
                        pageNum,
                        PAGE_SIZE
                );
                
                if (pageData == null || pageData.isEmpty()) {
                    break;
                }
                
                // 数据转换（格式化日期、处理空值等）
                List<List<Object>> excelData = convertToExcelData(pageData, req.getFields());
                
                // 写入当前页数据
                excelWriter.write(excelData, writeSheet);
                
                totalCount += pageData.size();
                log.info("导出进度: 第{}页, 当前批次{}条, 累计{}条", pageNum, pageData.size(), totalCount);
                
                // 如果当前页数据不足PAGE_SIZE，说明已经是最后一页
                if (pageData.size() < PAGE_SIZE) {
                    break;
                }
                
                pageNum++;
            }
            
            log.info("导出完成，总计{}条数据", totalCount);
        }
    }
    
    /**
     * 分页查询数据（根据数据源动态切换）
     */
    private List<Map<String, Object>> queryPageData(String datasource, String tableName, 
                                                     List<String> fields, Map<String, Object> filters, 
                                                     int pageNum, int pageSize) {
        // 切换数据源
        String actualDatasource = datasource;
        
        // 构建查询SQL
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(String.join(", ", fields));
        sql.append(" FROM ").append(tableName);
        
        // 添加WHERE条件
        List<Object> params = new ArrayList<>();
        if (filters != null && !filters.isEmpty()) {
            sql.append(" WHERE 1=1");
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String fieldKey = entry.getKey();
                // 校验过滤字段是否在白名单中
                if (ExportFieldEnum.isValidField(tableName, fieldKey)) {
                    sql.append(" AND ").append(fieldKey).append(" = ?");
                    params.add(entry.getValue());
                }
            }
        }
        
        // 添加分页
        sql.append(" LIMIT ?, ?");
        params.add((pageNum - 1) * pageSize);
        params.add(pageSize);
        
        log.debug("执行查询SQL: {}, 参数: {}", sql, params);
        
        // 根据数据源执行查询
        try {
            if ("product".equals(actualDatasource)) {
                return queryWithDataSource("product", sql.toString(), params.toArray());
            } else {
                return queryWithDataSource("map", sql.toString(), params.toArray());
            }
        } catch (Exception e) {
            log.error("查询数据失败: {}", e.getMessage(), e);
            throw new RuntimeException("查询数据失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 使用指定数据源执行查询
     */
    private List<Map<String, Object>> queryWithDataSource(String datasource, String sql, Object[] params) {
        try {
            // 切换数据源
            DynamicDataSourceContextHolder.push(datasource);
            log.debug("切换到数据源: {}", datasource);
            
            // 执行查询
            return jdbcTemplate.queryForList(sql, params);
        } finally {
            // 恢复数据源
            DynamicDataSourceContextHolder.poll();
        }
    }
    
    /**
     * 构建中文表头（中文名+英文字段名）
     */
    private List<List<String>> buildHeaders(String tableName, List<String> fields) {
        List<List<String>> headers = new ArrayList<>();
        for (String field : fields) {
            String chineseName = ExportFieldEnum.getFieldName(tableName, field);
            // 格式：中文名(英文字段名)
            String headerName = chineseName + "(" + field + ")";
            headers.add(Collections.singletonList(headerName));
        }
        return headers;
    }
    
    /**
     * 数据转换为Excel行数据
     */
    private List<List<Object>> convertToExcelData(List<Map<String, Object>> dataList, List<String> fields) {
        List<List<Object>> result = new ArrayList<>();
        
        for (Map<String, Object> data : dataList) {
            List<Object> row = new ArrayList<>();
            for (String field : fields) {
                Object value = data.get(field);
                
                // 处理null值
                if (value == null) {
                    row.add("");
                    continue;
                }
                
                // 日期时间格式化
                if (value instanceof java.sql.Timestamp) {
                    row.add(SIMPLE_DATE_FORMAT.format((java.sql.Timestamp) value));
                } else if (value instanceof java.util.Date) {
                    row.add(SIMPLE_DATE_FORMAT.format((java.util.Date) value));
                } else if (value instanceof LocalDateTime) {
                    row.add(((LocalDateTime) value).format(DATE_TIME_FORMATTER));
                } else if (value instanceof Boolean) {
                    // Boolean转换为中文
                    row.add(((Boolean) value) ? "是" : "否");
                } else if (value instanceof byte[]) {
                    // bit类型处理
                    byte[] bytes = (byte[]) value;
                    if (bytes.length > 0) {
                        row.add(bytes[0] == 1 ? "是" : "否");
                    } else {
                        row.add("");
                    }
                } else {
                    // 其他类型直接转字符串
                    row.add(value.toString());
                }
            }
            result.add(row);
        }
        
        return result;
    }
    
    /**
     * 生成文件名
     */
    private String generateFileName(String tableName) {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return tableName + "_export_" + dateStr + ".xlsx";
    }
    
    /**
     * 设置响应头
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        
        try {
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + encodedFileName);
        } catch (Exception e) {
            log.error("设置文件名失败", e);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        }
    }
    
    /**
     * 创建Excel样式策略
     */
    private HorizontalCellStyleStrategy createCellStyleStrategy() {
        // 表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);
        headWriteCellStyle.setWrapped(true); // 表头也支持自动换行
        
        // 内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.TOP); // 改为顶部对齐，适合多行文本
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setWrapped(true); // 启用自动换行，超长文本会自动换行显示
        
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
    
    /**
     * 使用模板导出Excel
     */
    @Override
    public void exportByTemplate(String templateKey, HttpServletResponse response) {
        log.info("开始使用模板导出Excel，模板Key: {}", templateKey);
        
        // 1. 获取模板配置
        ExportTemplateEnum template = ExportTemplateEnum.getByKey(templateKey);
        if (template == null) {
            throw new RuntimeException("不支持的模板: " + templateKey);
        }
        
        // 2. 设置响应头
        String fileName = generateFileName(templateKey);
        setResponseHeader(response, fileName);
        
        // 3. 执行模板导出
        try {
            doTemplateExport(template, response);
            log.info("模板导出成功，模板: {}, 文件名: {}", template.getTemplateName(), fileName);
        } catch (Exception e) {
            log.error("模板导出失败，模板Key: {}", templateKey, e);
            throw new RuntimeException("导出失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 执行模板导出
     */
    private void doTemplateExport(ExportTemplateEnum template, HttpServletResponse response) throws IOException {
        // 构建中文表头（中文名+英文字段名）
        List<List<String>> headers = new ArrayList<>();
        String[] fields = template.getFields();
        String[] fieldNames = template.getFieldNames();
        for (int i = 0; i < fieldNames.length; i++) {
            // 格式：中文名(英文字段名)
            String headerName = fieldNames[i] + "(" + fields[i] + ")";
            headers.add(Collections.singletonList(headerName));
        }
        
        // 配置Excel样式
        HorizontalCellStyleStrategy styleStrategy = createCellStyleStrategy();
        
        // 配置列宽自适应策略
        CustomColumnWidthStyleStrategy columnWidthStyleStrategy = new CustomColumnWidthStyleStrategy();
        
        // 使用EasyExcel流式写入
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .head(headers)
                .registerWriteHandler(styleStrategy)
                .registerWriteHandler(columnWidthStyleStrategy)
                .build()) {
            
            WriteSheet writeSheet = EasyExcel.writerSheet("数据").build();
            
            // 分页查询并写入
            int pageNum = 1;
            int totalCount = 0;
            
            while (true) {
                // 执行模板SQL查询
                List<Map<String, Object>> pageData = queryTemplateData(
                        template.getDatasource(),
                        template.getSql(),
                        pageNum,
                        PAGE_SIZE
                );
                
                if (pageData == null || pageData.isEmpty()) {
                    break;
                }
                
                // 数据转换
                List<List<Object>> excelData = convertTemplateData(pageData, template.getFields());
                
                // 写入当前页数据
                excelWriter.write(excelData, writeSheet);
                
                totalCount += pageData.size();
                log.info("模板导出进度: 第{}页, 当前批次{}条, 累计{}条", pageNum, pageData.size(), totalCount);
                
                // 如果当前页数据不足PAGE_SIZE，说明已经是最后一页
                if (pageData.size() < PAGE_SIZE) {
                    break;
                }
                
                pageNum++;
            }
            
            log.info("模板导出完成，总计{}条数据", totalCount);
        }
    }
    
    /**
     * 查询模板数据（带分页）
     */
    private List<Map<String, Object>> queryTemplateData(String datasource, String sql, int pageNum, int pageSize) {
        // 构建分页SQL
        String paginatedSql = sql + " LIMIT ?, ?";
        Object[] params = new Object[]{(pageNum - 1) * pageSize, pageSize};
        
        log.debug("执行模板查询SQL: {}, 参数: {}", paginatedSql, params);
        
        // 切换数据源并执行查询
        try {
            DynamicDataSourceContextHolder.push(datasource);
            log.debug("模板导出切换到数据源: {}", datasource);
            
            return jdbcTemplate.queryForList(paginatedSql, params);
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }
    
    /**
     * 转换模板数据为Excel行数据
     */
    private List<List<Object>> convertTemplateData(List<Map<String, Object>> dataList, String[] fields) {
        List<List<Object>> result = new ArrayList<>();
        
        for (Map<String, Object> data : dataList) {
            List<Object> row = new ArrayList<>();
            for (String field : fields) {
                Object value = data.get(field);
                
                // 处理null值
                if (value == null) {
                    row.add("");
                    continue;
                }
                
                // 日期时间格式化
                if (value instanceof java.sql.Timestamp) {
                    row.add(SIMPLE_DATE_FORMAT.format((java.sql.Timestamp) value));
                } else if (value instanceof java.util.Date) {
                    row.add(SIMPLE_DATE_FORMAT.format((java.util.Date) value));
                } else if (value instanceof LocalDateTime) {
                    row.add(((LocalDateTime) value).format(DATE_TIME_FORMATTER));
                } else if (value instanceof Boolean) {
                    row.add(((Boolean) value) ? "是" : "否");
                } else if (value instanceof byte[]) {
                    byte[] bytes = (byte[]) value;
                    if (bytes.length > 0) {
                        row.add(bytes[0] == 1 ? "是" : "否");
                    } else {
                        row.add("");
                    }
                } else {
                    row.add(value.toString());
                }
            }
            result.add(row);
        }
        
        return result;
    }
}
