package com.wisdom.classroom.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class FileUtil {

    /**
     * 提取上传方法为公共方法
     *
     * @param uploadDir 上传文件目录
     * @param file      上传对象
     * @throws Exception
     */
    public static String executeUpload(String uploadDir, MultipartFile file) throws Exception {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //文件名
        String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename = name + "_" + DateUtil.convertDate4(new Date()) + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filename);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        return filename;
    }

}
