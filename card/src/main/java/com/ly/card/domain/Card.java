package com.ly.card.domain;

import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Tarry
 * @create 2019/11/29 11:00
 */
@Data
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String company;
    private String position;
    private String address;
    private String mail;
    private String headImage;  //头像url
    private String wxCode;     //二维码url
    private String image;      //图片介绍url集合
    private String video;      //视频url
    private Date createTime;
    private Date updateTime;


}
