package com.example.elkdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.resps.StreamInfo;

import java.util.List;
import java.util.Map;

@Configuration
public class RedisProducerConfig {

    @Value("${stream.key}")
    private String streamKey;

    @Value("${host.redis}")
    private String redisHost;

    @Value("${port.redis}")
    private int redisPort;


    @Autowired
    RedisConsumerConfig redisConsumerConfig;



    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConsumerConfig.jedisConnectionFactory());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(RedisSerializer.string());
        return redisTemplate;

    }

//    @Bean
//    public void createStreamKeyIfNotExists() {
//
//        StreamOperations<String, Object, Object> streamOps = redisTemplate().opsForStream();
//
//        // Kiểm tra xem stream key đã tồn tại hay chưa bằng lệnh XINFO STREAM
//        Long streamLength = streamOps.size(streamKey);
//        if (streamLength == null || streamLength == 0) {
//            // Nếu stream key chưa tồn tại hoặc rỗng, tạo stream key mới
//            Map<String, Object> initialData = Map.of("message", "initial-data");
//            String messageId = String.valueOf(streamOps.add(streamKey, initialData));
//            System.out.println("Created new stream key: " + streamKey);
//            System.out.println("Message ID: " + messageId);
//        } else {
//            System.out.println("Stream key already exists: " + streamKey);
//        }
//    }


}
