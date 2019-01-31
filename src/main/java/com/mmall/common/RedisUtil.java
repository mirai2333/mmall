package com.mmall.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;
    @Autowired
    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 设置用户的token过期时间
     * @param username 用户名
     * @param userToken 用户token
     * @return false表示设置失败
     */
    public boolean setUserToken(String username,String userToken) {
        String userTokenKey = Const.RedisConst.USER_TOKEN + username;
        redisTemplate.opsForValue().set(userTokenKey, userToken);
        Boolean expire = redisTemplate.expire(userTokenKey, Const.RedisConst.USER_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        return expire != null ? expire : false;
    }
    public String getUserToken(String username){
        String userTokenKey = Const.RedisConst.USER_TOKEN + username;
        return redisTemplate.opsForValue().get(userTokenKey);
    }
}
