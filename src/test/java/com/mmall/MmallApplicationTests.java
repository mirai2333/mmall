package com.mmall;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MmallApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void contextLoads() {
        stringRedisTemplate.opsForValue().set("user_token:mirai2333","ddddd");
        Boolean expire = stringRedisTemplate.expire("user_token:mirai2333", 10, TimeUnit.SECONDS);
        System.out.println(expire);
    }

}

