package me.jmix.brothertakeaway.user.service.impl;

import me.jmix.brothertakeaway.user.dao.UserInfoRepository;
import me.jmix.brothertakeaway.user.entity.UserInfo;
import me.jmix.brothertakeaway.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JellyfishMIX
 * @date 2020/7/19 16:30
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository repository;

    /**
     * 通过openid来查询用户信息
     *
     * @param openid openid
     * @return UserInfo
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
