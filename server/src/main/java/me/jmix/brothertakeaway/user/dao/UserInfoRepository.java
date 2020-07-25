package me.jmix.brothertakeaway.user.dao;

import me.jmix.brothertakeaway.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JellyfishMIX
 * @date 2020/7/19 16:33
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByOpenid(String openid);
}
