package com.yuan.middleware.jdk.base.io.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;

import java.util.Date;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Data
// 内容行高20
@ContentRowHeight(20)
// 头行高20
@HeadRowHeight(20)
// 头字体设置成16
@HeadFontStyle(fontHeightInPoints = 16)
// 内容字体设置成14
@ContentFontStyle(fontHeightInPoints = 14)
public class WriteContent {
    @ColumnWidth(10)
    @ExcelProperty({"market_activity", "自增ID"})
    private Long id;

    /**
     * 宽度为50
     */
    @ColumnWidth(50)
    @ExcelProperty({"market_activity", "字符串"})
    private String name;

    @ColumnWidth(25)
    @ExcelProperty({"market_activity", "日期"})
    private Date startTime;

}
