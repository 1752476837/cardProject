package com.ly.card.domain.vo;

import lombok.Data;

/**
 * @author Tarry
 * @create 2019/12/2 13:44
 */
@Data
public class BaseCard {
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String company;
    private String position;
    private String address;
    private String mail;
    private String headImage;


}
