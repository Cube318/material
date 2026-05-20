package com.cube.material.handler;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义列宽处理策略
 * 实现列宽自适应，并限制最大列宽
 * 
 * @author cube
 * @date 2026-05-20
 */
@Slf4j
public class CustomColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {
    
    /**
     * 每列的最大列宽缓存
     */
    private Map<Integer, Map<Integer, Integer>> cache = new HashMap<>();
    
    /**
     * 最大列宽（字符数）
     */
    private static final int MAX_COLUMN_WIDTH = 50;
    
    /**
     * 最小列宽（字符数）
     */
    private static final int MIN_COLUMN_WIDTH = 10;
    
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, 
                                   Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !cellDataList.isEmpty();
        if (!needSetWidth) {
            return;
        }
        
        // 获取sheet索引
        Integer sheetNo = writeSheetHolder.getSheetNo();
        
        // 初始化缓存
        Map<Integer, Integer> maxColumnWidthMap = cache.computeIfAbsent(sheetNo, k -> new HashMap<>());
        
        // 获取列索引
        Integer columnIndex = cell.getColumnIndex();
        
        // 计算当前单元格内容长度
        Integer columnWidth = getColumnWidth(cellDataList, cell, isHead);
        
        // 限制列宽范围
        if (columnWidth < MIN_COLUMN_WIDTH) {
            columnWidth = MIN_COLUMN_WIDTH;
        }
        if (columnWidth > MAX_COLUMN_WIDTH) {
            columnWidth = MAX_COLUMN_WIDTH;
        }
        
        // 获取该列已有的最大宽度
        Integer maxColumnWidth = maxColumnWidthMap.get(columnIndex);
        if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
            maxColumnWidthMap.put(columnIndex, columnWidth);
            // 设置列宽，需要转换为Excel的列宽单位（256 * 字符数）
            writeSheetHolder.getSheet().setColumnWidth(columnIndex, columnWidth * 256);
        }
    }
    
    /**
     * 计算列宽
     */
    private Integer getColumnWidth(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
        // 表头宽度稍微大一点
        if (isHead) {
            return calculateCellWidth(cell) + 2;
        }
        
        // 内容单元格
        int maxWidth = 0;
        for (WriteCellData<?> cellData : cellDataList) {
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                continue;
            }
            
            switch (type) {
                case STRING:
                    String stringValue = cellData.getStringValue();
                    if (stringValue != null) {
                        maxWidth = Math.max(maxWidth, getStringWidth(stringValue));
                    }
                    break;
                case NUMBER:
                    String numberString = cellData.getNumberValue() != null ? 
                            cellData.getNumberValue().toString() : "";
                    maxWidth = Math.max(maxWidth, getStringWidth(numberString));
                    break;
                case BOOLEAN:
                    maxWidth = Math.max(maxWidth, 4); // "是"或"否"的宽度
                    break;
                default:
                    maxWidth = Math.max(maxWidth, calculateCellWidth(cell));
                    break;
            }
        }
        
        return maxWidth;
    }
    
    /**
     * 计算字符串宽度（考虑中英文）
     */
    private Integer getStringWidth(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        
        int width = 0;
        for (char c : str.toCharArray()) {
            // 中文字符宽度为2，英文字符宽度为1
            if (isChinese(c)) {
                width += 2;
            } else {
                width += 1;
            }
        }
        
        return width;
    }
    
    /**
     * 判断是否为中文字符
     */
    private boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }
    
    /**
     * 计算单元格宽度（兜底方法）
     */
    private Integer calculateCellWidth(Cell cell) {
        String cellValue = cell.getStringCellValue();
        if (cellValue == null || cellValue.isEmpty()) {
            return MIN_COLUMN_WIDTH;
        }
        return getStringWidth(cellValue);
    }
}
