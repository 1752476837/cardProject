package com.ly.card.mapper;

import com.ly.card.domain.Config;
import com.ly.card.utils.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Tarry
 * @create 2019/12/3 9:32
 */
@Repository
public interface ConfigMapper extends BaseMapper<Config,Long> {
}
