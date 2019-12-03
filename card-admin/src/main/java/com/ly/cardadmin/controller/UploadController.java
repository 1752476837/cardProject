package com.ly.cardadmin.controller;


import com.ly.cardadmin.exception.ExceptionEnum;
import com.ly.cardadmin.exception.LyException;
import com.ly.cardadmin.service.UploadService;
import com.ly.cardadmin.utils.FileNameUtils;
import com.ly.cardadmin.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Tarry
 * @create 2019/11/29 15:51
 */
@RestController
public class UploadController {
    //1定义要上传文件 的存放路径
    @Value("${web.upload-path}")
    private String path;
    @Value("${web.domain}")
    private String domain;
    @Value("#{'${web.image}'.split(',')}")
    private List<String> images;
    @Value("#{'${web.video}'.split(',')}")
    private List<String> videos;

    @Autowired
    private UploadService uploadService;
    /**
     *
     * @param file 上传的文件
     * @return
     */
    @ResponseBody
    @RequestMapping("/fileUpload")
    public String upload(@RequestParam("file") MultipartFile file){

        //1获得文件名字
        String fileName=file.getOriginalFilename();
        String suffix = StringUtils.substringAfterLast(fileName,".").toLowerCase();

        //判断文件格式
        if (!images.contains(suffix) && !videos.contains(suffix)) {
            throw new LyException(ExceptionEnum.INVALID_FILE_FORMAT);
        }
        String newFileName = FileUtils.upload(file, path, fileName);
        if(newFileName != null){
            //这里将图片的网址返回并，存到数据库
            String fileUrl = domain + "/" + newFileName;
            System.out.println("文件网址："+fileUrl);
            if (images.contains(suffix)){
                //uploadService.saveFile(fileUrl,"image");
            }
            if (videos.contains(suffix)){
                //uploadService.saveFile(fileUrl,"video");
            }
            return fileUrl;
        }

        /**
        //图片
        if (images.contains(suffix)){
            String newFileName = FileUtils.upload(file, path, fileName);
            if(newFileName != null){
                //这里将图片的网址返回并，存到数据库
                String imgUrl = domain + "/" + newFileName;
                System.out.println("图片的网址："+imgUrl);
                return imgUrl;
            }
        }
        //视频
        if (videos.contains(suffix)){
            String newFileName = FileUtils.upload(file, path, fileName);
            //mp4文件所在路径
            String mp4Path = path+"/"+FileNameUtils.getPreName(newFileName)+ ".mp4";
            //m3u8 文件的名称
            String m3u8Name = FileNameUtils.getPreName(newFileName)+ ".m3u8";
            //m3u8文件所在目录
            String m3u8Out = path+"/"+FileNameUtils.getPreName(newFileName)+"/";

            if(newFileName != null) {
                //如果是avi对齐进行转码
                if (suffix.equals("avi")){
                    //1.avi转mp4
                    uploadService.avi2mp4(path,newFileName);
                    //2.mp4转码m3u8
                    uploadService.videoStream(mp4Path,m3u8Name,m3u8Out);

                }else if (suffix.equals("mp4")){
                    //如果是mp4，转换m3u8
                    uploadService.videoStream(mp4Path,m3u8Name,m3u8Out);

                }
                //返回可以播放的m3u8的视频网址【存到数据库】
                String m3u8Url = domain + "/"+FileNameUtils.getPreName(m3u8Name)+"/" +m3u8Name;
                System.out.println(m3u8Url);
                return m3u8Url;

            }
         }
        */

        throw new LyException(ExceptionEnum.INVALID_FILE_FORMAT);
    }

}
