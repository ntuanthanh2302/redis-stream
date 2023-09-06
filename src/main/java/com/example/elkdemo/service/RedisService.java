package com.example.elkdemo.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class RedisService   {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${stream.key}")
    private String streamKey;

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void publishEvent(String request){
        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .ofObject(request)
                .withStreamKey(streamKey);
        this.redisTemplate
                .opsForStream()
                .add(record);
        atomicInteger.incrementAndGet();
    }

}
