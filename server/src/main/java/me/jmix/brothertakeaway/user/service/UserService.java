package me.jmix.brothertakeaway.user.service;

import me.jmix.brothertakeaway.user.entity.UserInfo;

/**
 * @author JellyfishMIX
 * @date 2020/7/19 16:29
 */
public interface UserService {
    /**
     * 通过openid来查询用户信息
     *
     * @param openid openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
