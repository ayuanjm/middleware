package com.yuan.middleware.base.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 文件上传下载
 *
 * @author yuan
 * @date 2019/11/29
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 跳转上传页面
     * <!--访问html页面-->
     * <dependency>
     * <groupId>org.springframework.boot</groupId>
     * <artifactId>spring-boot-starter-thymeleaf</artifactId>
     * </dependency>
     * html文件需要放在classpath:/templates/文件下
     * <p>
     * 访问欢迎页index.html时不需要引入thymeleaf也能访问，只需放在以下路径
     * "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"
     *
     * @return
     */
    @RequestMapping("/yuan")
    public ModelAndView load() {
        return new ModelAndView("upload");
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        log.info("文件上传开始");
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 文件保存路径
        String filePath = "/Users/yuan/Downloads/upload/";
        // 文件重命名，防止重复
        fileName = filePath + UUID.randomUUID() + fileName;
        // 文件对象
        File fileDest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if (!fileDest.getParentFile().exists()) {
            fileDest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(fileDest);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 返回文件到浏览器
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws Exception {
        // 文件地址，真实环境是存放在数据库中的
        File file = new File("/Users/yuan/Downloads/upload/1.xlsx");
        // 创建输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 获取后缀名
        String suffixName = file.getPath().substring(file.getPath().lastIndexOf("."));
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + "download" + suffixName);
        // 创建输出对象 os和response.getOutputStream() 指向同一个输出流
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len;
        while ((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }


}


