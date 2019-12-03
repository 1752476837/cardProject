package com.ly.cardadmin.mapper;

import com.ly.cardadmin.domain.Config;
import com.ly.cardadmin.utils.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Tarry
 * @create 2019/12/3 9:32
 */
@Repository
public interface ConfigMapper extends BaseMapper<Config,Long> {

    @Update("update config set player=#{config.player},title=#{config.title},description=#{config.description},domain=#{config.domain},icon=#{config.icon} where id = 0")
    void saveConfig(@Param("config") Config config);
}
