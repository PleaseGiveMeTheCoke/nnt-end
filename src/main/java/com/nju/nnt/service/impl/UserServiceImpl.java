package com.nju.nnt.service.impl;

import com.nju.nnt.entity.User;
import com.nju.nnt.mapper.UserMapper;
import com.nju.nnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User selectUserByOpenId(String openId) {
        User user = userMapper.selectById(openId);
        return user;
    }

    @Override
    public void registerUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserDetail(Long userId) {
        User user = userMapper.selectById(userId);
        return user;
    }
}
