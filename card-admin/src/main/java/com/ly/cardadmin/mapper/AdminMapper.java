package com.ly.cardadmin.mapper;

import com.ly.cardadmin.domain.Admin;
import com.ly.cardadmin.utils.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author Tarry
 * @create 2019/11/29 13:43
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin,Long> {

    @Select("select * from admin where username = #{username}")
    Admin queryAdminByUsername(String username);

    @Select("select* from admin where id = #{id}")
    Admin queryPassword(Long id);

    @Update("update admin set password = #{admin.password},salt = #{admin.salt} where id=#{id}")
    void updatePassWord(@Param("id") Long id,@Param("admin") Admin newAdmin);
}
