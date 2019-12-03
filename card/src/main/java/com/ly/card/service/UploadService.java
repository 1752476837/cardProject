package com.ly.card.service;

import com.ly.card.exception.ExceptionEnum;
import com.ly.card.exception.LyException;
import com.ly.card.mapper.CardMapper;
import com.ly.card.utils.FileNameUtils;
import com.ly.card.utils.FileUtils;
import com.ly.card.utils.HlsVideoUtil;
import com.ly.card.utils.Mp4VideoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Tarry
 * @create 2019/11/30 11:17
 */
@Service
public class UploadService {

    //1定义要上传文件 的存放路径
    @Value("${web.upload-path}")
    private String path;
    @Value("${web.domain}")
    private String domain;
    @Value("#{'${web.image}'.split(',')}")
    private List<String> images;
    @Value("#{'${web.video}'.split(',')}")
    private List<String> videos;

//    @Value("${web.ffmpeg_path}")
    String ffmpeg_path;//ffmpeg的安装位置
    @Autowired
    private CardMapper cardMapper;

    public String uploadFile( MultipartFile file){
        //1获得文件名字
        String fileName=file.getOriginalFilename();
        String suffix = StringUtils.substringAfterLast(fileName,".").toLowerCase();

        //判断文件格式
        if (!images.contains(suffix) && !videos.contains(suffix)) {
            throw new LyException(ExceptionEnum.INVALID_FILE_FORMAT);
        }
        String newFileName = FileUtils.upload(file, path, fileName);
        if(newFileName != null){
            //这里将文件的网址返回
            String fileUrl = domain + "/" + newFileName;
            System.out.println("文件网址："+fileUrl);
            return fileUrl;
        }
        throw new LyException(ExceptionEnum.INVALID_FILE_FORMAT);
    }

    public void avi2mp4(String serverPath,String aviName) {
        String mp4Name = FileNameUtils.getPreName(aviName)+ ".mp4";
        String aviPath = serverPath+"/"+aviName;
        //创建工具类对象
        Mp4VideoUtil mp4VideoUtil =new Mp4VideoUtil(ffmpeg_path,aviPath,mp4Name,serverPath);
        //进行处理
        String result = mp4VideoUtil.generateMp4();
        if(result == null || !result.equals("success")){
            //处理失败
            throw new LyException(ExceptionEnum.AVI2MP4_FILE);
        }
    }

    //将mp4生成m3u8和ts文件
    public void videoStream(String mp4_video_path,String m3u8_name,String m3u8folder_path){
         HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path,mp4_video_path,m3u8_name,m3u8folder_path);
        //生成m3u8和ts文件
        String tsResult = hlsVideoUtil.generateM3u8();
        if(tsResult == null || !tsResult.equals("success")){
            //处理失败
            throw new LyException(ExceptionEnum.VIDEO_STREAM_FAIL);
        }
    }

}
