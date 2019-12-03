package com.ly.card.service;

import com.ly.card.domain.User;
import com.ly.card.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tarry
 * @create 2019/11/29 9:58
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

//    查询数据库，看用户是否存在
    public User getUserByOpenId(String openid){
        return userMapper.getUserByOpenId(openid);
    }

    //新增用户openId
    @Transactional
    public User insertUser(User user) {
        userMapper.insert(user);
        return userMapper.getUserByOpenId(user.getOpenid());
    }
}
