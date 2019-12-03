package com.ly.cardadmin.service;

import com.ly.cardadmin.exception.ExceptionEnum;
import com.ly.cardadmin.exception.LyException;
import com.ly.cardadmin.mapper.CardMapper;
import com.ly.cardadmin.utils.FileNameUtils;
import com.ly.cardadmin.utils.HlsVideoUtil;
import com.ly.cardadmin.utils.Mp4VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Tarry
 * @create 2019/11/30 11:17
 */
@Service
public class UploadService {
//    @Value("${web.ffmpeg_path}")
    String ffmpeg_path;//ffmpeg的安装位置
    @Autowired
    private CardMapper cardMapper;

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
