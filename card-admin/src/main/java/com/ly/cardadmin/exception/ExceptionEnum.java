package com.ly.cardadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    INVALID_USERNAME_PASSWORD(400,"用户名或密码不正确"),
    INVALID_FILE_FORMAT(400,"文件格式不符合要求"),
    AVI2MP4_FILE(400,"视频编码转换失败"),
    VIDEO_STREAM_FAIL(400,"视频流转换失败")
    ;
    private Integer code;
    private String msg;

}
