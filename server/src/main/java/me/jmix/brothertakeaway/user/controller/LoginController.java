package me.jmix.brothertakeaway.user.controller;

import me.jmix.brothertakeaway.user.constant.CookieConstant;
import me.jmix.brothertakeaway.user.constant.RedisConstant;
import me.jmix.brothertakeaway.user.entity.UserInfo;
import me.jmix.brothertakeaway.user.enums.ResultEnum;
import me.jmix.brothertakeaway.user.enums.RoleEnum;
import me.jmix.brothertakeaway.user.service.UserService;
import me.jmix.brothertakeaway.user.utils.CookieUtil;
import me.jmix.brothertakeaway.user.utils.ResultVOUtil;
import me.jmix.brothertakeaway.user.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author JellyfishMIX
 * @date 2020/7/19 16:37
 */
@RestController
@RequestMapping(value = "/login", produces = { "application/json;charset=UTF-8" })
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 买家登录
     *
     * @param openid 微信用户openid
     * @param response HttpServletResponse
     * @return result
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid,
                          HttpServletResponse response) {
        // 1. openid和数据库里的数据进行匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        // 2. 判断角色
        if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        // 3. cookie里设置openid = abc
        CookieUtil.set(response, CookieConstant.TOKEN, openid, CookieConstant.expire);

        return ResultVOUtil.success();
    }

    /**
     * 卖家登录
     *
     * @param openid 微信用户openid
     * @param response HttpServletResponse
     * @return result
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                          HttpServletResponse response) {
        // 1. openid和数据库里的数据进行匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        // 2. 判断角色
        if (!RoleEnum.SELLER.getCode().equals(userInfo.getRole())) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        // 3. redis设置key = UUID，value = openid
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, token), openid, CookieConstant.expire, TimeUnit.SECONDS);

        // 3. cookie里设置openid = abc
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.expire);

        return ResultVOUtil.success();
    }
}
