package com.ly.cardadmin.service;

import com.ly.cardadmin.domain.Config;
import com.ly.cardadmin.mapper.ConfigMapper;
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

    public void saveConfig(Config config) {
        configMapper.saveConfig(config);
    }

    public Config getConfig() {
        return configMapper.selectByPrimaryKey(0);
    }
}
