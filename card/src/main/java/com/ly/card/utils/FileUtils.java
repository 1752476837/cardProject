package com.ly.card.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author gaoxuyang
 *
 */
public class FileUtils {

	/**
	 * 
	 * @param file 文件
	 * @param path   文件存放路径
	 * @param fileName 原文件名
	 * @return
	 */
	 public static String upload(MultipartFile file, String path, String fileName){

	        // 生成新的文件名
		 String uuidName = FileNameUtils.getFileName(fileName);
		 String realPath = path + "/" + uuidName;

	        File dest = new File(realPath);

	        //判断文件父目录是否存在
	        if(!dest.getParentFile().exists()){
	            dest.getParentFile().mkdir();
	        }

	        try {
	            //保存文件
	            file.transferTo(dest);
	            return uuidName;
	        } catch (IllegalStateException e) {	            
	            e.printStackTrace();
	            return null;
	        } catch (IOException e) {
	       	    e.printStackTrace();
	            return null;
	        }

	    }
}