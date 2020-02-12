package com.ly.cardadmin.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 名片实体类
 * @author Tarry
 * @create 2019/11/29 11:00
 */
@Data
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelIgnore
    private Long id;
    @ExcelIgnore
    private Long userId;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "手机号",width = 20)
    private String phone;
    @Excel(name = "公司",width = 20)
    private String company;
    @Excel(name = "职位",width = 20)
    private String position;
    @Excel(name = "地址",width = 20)
    private String address;
    @Excel(name = "邮箱",width = 20)
    private String mail;

    @ExcelIgnore
    private String headImage;  //头像url
    @ExcelIgnore
    private String wxCode;     //二维码url
    @ExcelIgnore
    private String image;      //图片介绍url集合
    @ExcelIgnore
    private String video;      //视频url

    @Excel(name = "创建时间",format = "yyyy-MM-dd",width = 20)
    private Date createTime;
    @Excel(name = "更新时间",format = "yyyy-MM-dd",width = 20)
    private Date updateTime;


}
