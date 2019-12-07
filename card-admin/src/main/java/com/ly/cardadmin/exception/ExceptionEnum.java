package com.ly.cardadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    INVALID_USERNAME_PASSWORD(400,"用户名或密码不正确"),
    INVALID_LOGIN_STATE(400,"无效的登录状态"),
    INVALID_FILE_FORMAT(400,"文件格式不符合要求"),
    AVI2MP4_FILE(400,"视频编码转换失败"),
    VIDEO_STREAM_FAIL(400,"视频流转换失败"),
    PASS_NOT_FIT(400,"两次密码输入不一致")
    ;
    private Integer code;
    private String msg;

}
