package me.jmix.brothertakeaway.user.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JellyfishMIX
 * @date 2020/7/23 16:42
 */
public class CookieUtil {
    /**
     * 设置cookie
     *
     * @param response HttpServletResponse
     * @param name cookie name
     * @param value cookie value
     * @param maxAge 生存时间
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param request HttpServletRequest
     * @param name cookie name
     * @return Cookie
     */
    public static Cookie get(HttpServletRequest request,
                           String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
