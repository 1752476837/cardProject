package com.ly.card.utils;

import org.apache.commons.lang3.StringUtils;

public class FileNameUtils {

	 /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return UUIDUtils.getUUID() + FileNameUtils.getSuffix(fileOriginName);
    }

    public static String getPreName(String name){
        return StringUtils.substringBeforeLast(name,".");
    }


}