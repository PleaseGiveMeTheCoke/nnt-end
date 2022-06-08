package com.nju.nnt.service;

import com.nju.nnt.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User selectUserByOpenId(String openId);

    void registerUser(User user);

    User getUserDetail(Long userId);
}
