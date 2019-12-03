package com.ly.cardadmin.mapper;

import com.ly.cardadmin.domain.Admin;
import com.ly.cardadmin.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Tarry
 * @create 2019/11/29 13:43
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin,Long> {

    @Select("select * from admin where username = #{username}")
    Admin queryAdminByUsername(String username);
}
