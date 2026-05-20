package com.cube.material.service;

import com.cube.material.dto.ExportReq;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Excel导出Service接口
 * 
 * @author cube
 * @date 2026-05-18
 */
public interface ExcelExportService {
    
    /**
     * 导出Excel（普通导出）
     * 
     * @param req 导出请求参数
     * @param response HTTP响应对象
     */
    void exportExcel(ExportReq req, HttpServletResponse response);
    
    /**
     * 使用模板导出Excel
     * 
     * @param templateKey 模板Key
     * @param response HTTP响应对象
     */
    void exportByTemplate(String templateKey, HttpServletResponse response);
}
