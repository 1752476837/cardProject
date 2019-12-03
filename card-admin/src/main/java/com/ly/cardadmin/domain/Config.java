package com.ly.cardadmin.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Tarry
 * @create 2019/12/3 8:51
 */
@Data
@Table(name = "config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer player;
    private String title;
    private String description;
    private String domain;
    private String icon;
}
