package com.example.elkdemo.controller;

import com.example.elkdemo.entity.LogMessageEntity;
import com.example.elkdemo.entity.UserElasticSearchEntity;
import com.example.elkdemo.entity.UserEntity;
//import com.example.elkdemo.service.UserService;
import com.example.elkdemo.service.ConsumerService;
import com.example.elkdemo.service.RedisService;
import com.example.elkdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ELKController {


    private final RedisTemplate<String, String> redisTemplate;

    private final UserService userService;

    private final RedisService redisService;

    private final ConsumerService consumerService;

    private static final String CACHE_KEY = "test";

    @GetMapping("/log")
    public String HomePage(@RequestParam String value){

        String cachedTest = redisTemplate.opsForValue().get(CACHE_KEY + value);

        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Welcome home Page " + localDateTime);
        if (cachedTest != null) {
            return cachedTest;
        }
        redisTemplate.opsForValue().set(CACHE_KEY + value ,value,Duration.ofMinutes(5));
        return value;
    }

    @GetMapping("/getAll")
    public List<UserElasticSearchEntity> GetAll(@RequestParam String fieldName)  {
        return userService.findByFieldName(fieldName);
    }

    @GetMapping("/getByNameAndId")
    public List<UserElasticSearchEntity> GetByNameAndId(@RequestParam String name,@RequestParam Long id)  {
        return userService.searchByContent(name,id);
    }

    @GetMapping("/getMessage")
    public List<LogMessageEntity> GetMessage(@RequestParam String term)  {
        return userService.logMessageGet(term);
    }


    @RequestMapping(value = "/publisher",method = RequestMethod.POST)
    public ResponseEntity<Void> publish(@RequestParam String message) {

        try {
            log.info("publishing >>" + message);
            redisService.publishEvent(message.toString());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/getMessage1")
    public List<UserEntity> GetMessageTest(@RequestParam String term)  {
        return consumerService.test(term);
    }

//    @GetMapping("/log")
//    @Cacheable("stringCache")
//    public String HomePage(@RequestParam String value){
//        LocalDateTime localDateTime = LocalDateTime.now();
//        log.info("Welcome home Page " + localDateTime);
//        simulateSlowService();
//        return value;
//    }
//
//
//    private void simulateSlowService() {
//        try {
//            Thread.sleep(3000); // Giả lập thời gian chậm 3 giây
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
