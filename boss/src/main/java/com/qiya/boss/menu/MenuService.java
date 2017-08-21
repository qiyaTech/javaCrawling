package com.qiya.boss.menu;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiya.framework.coreservice.RedisService;

/**
 * Created by qiyamac on 2017/2/7.
 */
@Service
public class MenuService {

    @Autowired
    private RedisService redisService;

    private String redisMenuKey = "MenuResource:";

    public void clealRedisMenu(){
        Set<String> set= redisService.keys(redisMenuKey+"*");
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            redisService.del(keyStr);
        }
    }

}
