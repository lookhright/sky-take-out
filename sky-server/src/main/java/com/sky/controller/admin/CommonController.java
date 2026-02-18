package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/*
 通用接口

 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOssUtil AliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
       log.info("文件上传：{}",file);

       try {
           //原始文件名
          String originalFilename = file.getOriginalFilename();
          //截取原始文件名的后缀
           String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
           //构建新文件名称
          String objectName = UUID.randomUUID().toString() +extension;

          //文件的请求路径
          java.lang.String filePath = AliOssUtil.upload(file.getBytes(),objectName);
           return Result.success(filePath);


       } catch (IOException e){
           log.info("文件上传失败：{}",e);
       }

        return  Result.error("文件上传失败");

    }


}
