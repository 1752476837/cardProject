package com.ly.card.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PHONE_CODE_ERROR(400,"短信验证码不正确"),
    PHONE_IS_EXIST(400,"该手机号已被注册！"),
    AUTH_USERNAME_NONE(400,"用户名或密码不正确"),
    LOGIN_FAIL(400,"登录失败"),
    INVALID_FILE_FORMAT(400,"文件格式不符合要求"),
    AVI2MP4_FILE(400,"视频编码转换失败"),
    VIDEO_STREAM_FAIL(400,"视频流转换失败"),
    USERID_IS_NOT_NULL(400,"用户id不能为空"),
    CONTENT_IS_NOT_NULL(400,"内容不能为空！"),
    NAME_IS_NOT_NULL(400,"姓名不能为空"),
    GENERATE_TOKEN_FAIL(400,"生成Token失败！")
    ;
    private Integer code;
    private String msg;

}
