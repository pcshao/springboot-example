package com.example.redis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private StringRedisTemplate testMax;

    // 缓存key
    private final String _CacheKey = "userCacheKeyTime";

    @RequestMapping("testmax")
    public String testMaxMax() {
        String skey = "tMax-";
        String svalue = "nononon";
        Random random = new Random();
        Long starttime = System.currentTimeMillis();
        int i = 0;
        while( i++ < 1_000 ) {
            testMax.opsForValue().set(skey+ random.nextInt()*999_999_999, svalue+ random.nextInt()*9765);
        }
        Long endtime = System.currentTimeMillis();
        return "存储成功！"+(endtime-starttime)+"ms" ;
    }

    @RequestMapping("/ca")
    @Cacheable(value = _CacheKey)
    public String doGet() {

//        stringRedisTemplate.hasKey(_CacheKey);
//        stringRedisTemplate.opsForValue().set(_CacheKey,"value");
//        stringRedisTemplate.opsForValue().get(_CacheKey);

        System.out.println("没有出现这行代码，说明缓存已经生效。");
        return "cache:" + new Date().getTime();
    }

    @RequestMapping("/put")
    @CachePut(value = _CacheKey)
    public String putCache() {
        System.out.println("缓存更新");
        return "update cache:" + new Date().getTime();
    }

    @RequestMapping("/del")
    @CacheEvict(value = _CacheKey)
    public String delCache() {
        System.out.println("缓存删除");
        return "delete cache:" + new Date().getTime();
    }

    /**
     * 测试Session
     * @param session
     */
    @RequestMapping("/uid")
    public String testSession(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        System.out.println("uid:" + uid);
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        String sessionId = session.getId();
        System.out.println("session:" + sessionId);
        return sessionId;
    }

    @RequestMapping("name")
    public String getName(){
        return  "Adam";
    }

}
