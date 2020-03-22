package com.smallchili.blog.controller;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * 用于测试
 * 
 */
@RestController
@CrossOrigin
public class FileController {

    @PostMapping(value = "/image")
    public Map<String,String> fileUpload(@RequestParam(value = "fileName") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        
        System.out.println("输出文件名:"+fileName);
        String filePath = "F://image//";
        System.out.println("输出文件要保留的服务器地址:"+filePath);
        
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        System.out.println("输出新文件名:"+fileName);
        File dest = new File(filePath + fileName);
        System.out.println("生成新文件:"+dest.getName());
        
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
        	System.out.println(dest.getPath());
        	System.out.println("复制文件");
            file.transferTo(dest);
        } catch (IOException e) {
        	System.out.println("异常");
            e.printStackTrace();
        }
        String filename = "/image/" + fileName;
        System.out.println(filename);
        Map<String,String> hashmap =  new HashMap<String,String>();
        hashmap.put("pic", filename);
        
        return hashmap;
    }
}
