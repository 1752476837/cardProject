package com.ly.cardadmin.service;

import com.ly.cardadmin.config.JwtProperties;
import com.ly.cardadmin.domain.Admin;
import com.ly.cardadmin.domain.UserInfo;
import com.ly.cardadmin.exception.ExceptionEnum;
import com.ly.cardadmin.exception.LyException;
import com.ly.cardadmin.mapper.AdminMapper;
import com.ly.cardadmin.utils.CodeUtil;
import com.ly.cardadmin.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tarry
 * @create 2019/11/29 13:41
 */

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    JwtProperties props;

    public String login(String username, String password) {
        //查询用户
        Admin admin =adminMapper.queryAdminByUsername(username);
        //校验用户名
        if (admin == null){
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        String pass = CodeUtil.md5Hex(password, admin.getSalt());
        String pass1 = admin.getPassword();

        //校验密码
        if(!pass1.equals(pass)){
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        //用户基本信息存入UserInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.valueOf(admin.getId()));
        userInfo.setName(admin.getUsername());

        //生成Token
        try {
            String token = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
            return token;
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }


    }

    public  Admin queryPassword(Long id) {
       return adminMapper.queryPassword(id);
    }

    //更新密码和盐
    public void updatePassWord(Long id,Admin newAdmin) {
        adminMapper.updatePassWord(id,newAdmin);
    }
}
