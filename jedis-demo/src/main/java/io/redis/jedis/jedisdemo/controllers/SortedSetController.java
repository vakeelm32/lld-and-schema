package io.redis.jedis.jedisdemo.controllers;

import io.redis.jedis.jedisdemo.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SortedSetController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    List<String> monthsLowerCase = Arrays.asList(
            "january", "february", "march", "april",
            "may", "june", "july", "august",
            "september", "october", "november", "december"
    );


    @PostMapping("/postData")
    public void postData(@RequestBody Enrollment data) {
        if (!monthsLowerCase.contains(data.getMonth())) {
            throw new RuntimeException("Month is not correct please choose from this list - " + monthsLowerCase.toString());
        }
        redisTemplate.opsForZSet().add(data.getMonth().toLowerCase(), data.toString(), Double.valueOf(data.getScore()));
    }

    @GetMapping("/sortedData/{month}")
    public Set<Object> getData(@PathVariable String month) {
        if (!monthsLowerCase.contains(month)) {
            throw new RuntimeException("Month is not correct please choose from this list - " + monthsLowerCase.toString());
        }
        Set<Object> objects = redisTemplate.opsForZSet().rangeByScore(month.toLowerCase(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return objects;
    }

    @GetMapping("/sortedData/all")
    public Map<String, Set<Object>> getAllData() {
        LinkedHashMap<String, Set<Object>> map = new LinkedHashMap<>();
        for (String month : monthsLowerCase) {
            Set<Object> objects = redisTemplate.opsForZSet().rangeByScore(month, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            map.put(month, objects);
        }
        return map;
    }


}
