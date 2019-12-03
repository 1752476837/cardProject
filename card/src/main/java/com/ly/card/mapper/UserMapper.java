package com.ly.card.mapper;

import com.ly.card.domain.User;
import com.ly.card.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Tarry
 * @create 2019/11/29 9:59
 */
@Repository
public interface UserMapper extends BaseMapper<User,String> {

    //根据openid查询用户是否存在
    @Select("select * from user where openid = #{id}")
    User getUserByOpenId(String id);
}
