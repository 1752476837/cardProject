package com.ly.card.utils;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;


//集合 通用Mapper，必须加该注解
@RegisterMapper
public interface BaseMapper<T,PK> extends Mapper<T> ,IdListMapper<T,PK>,InsertListMapper<T> {
}
//InsertListMapper，有两个包，一个 必须设置主键 OK， 另一个自增字段名称必须为 id