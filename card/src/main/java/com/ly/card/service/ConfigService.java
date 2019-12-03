package com.ly.card.service;

import com.ly.card.domain.Config;
import com.ly.card.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tarry
 * @create 2019/12/3 8:54
 */
@Service
public class ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    public Config getConfig() {
        return configMapper.selectByPrimaryKey(0);
    }
}
