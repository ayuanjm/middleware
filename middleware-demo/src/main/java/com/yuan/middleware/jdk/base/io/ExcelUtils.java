package com.yuan.middleware.jdk.base.io;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.Data;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hutool Java工具类库导出Excel
 *
 * @author yuan
 * @date 2020/02/04
 */
@Data
public class ExcelUtils {
    public static void exportExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        List<User> list = new ArrayList<>();

        list.add(new User("zhangsan", "1231", new Date()));

        list.add(new User("zhangsan1", "1232", new Date()));

        list.add(new User("zhangsan2", "1233", new Date()));

        list.add(new User("zhangsan3", "1234", new Date()));

        list.add(new User("zhangsan4", "1235", new Date()));

        list.add(new User("zhangsan5", "1236", DateUtil.date(new Date())));

        // 通过工具类创建writer，默认创建xls格式

        ExcelWriter writer = ExcelUtil.getWriter();

        //自定义标题别名

        writer.addHeaderAlias("name", "姓名");

        writer.addHeaderAlias("age", "年龄");

        writer.addHeaderAlias("birthDay", "生日");

        // 合并单元格后的标题行，使用默认标题样式

        writer.merge(2, "申请人员信息");

        // 一次性写出内容，使用默认样式，强制输出标题

        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流

        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        //name.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String name = "申请学院";
        //Safari浏览器还是乱码，但是其他浏览器不乱码
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(name + ".xls", "UTF-8"));
        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
        }

        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    @Data
    static class User {
        private String name;
        private String passWord;
        private Date birthday;

        public User(String name, String passWord, Date birthday) {
            this.name = name;
            this.passWord = passWord;
            this.birthday = birthday;
        }
    }
}
