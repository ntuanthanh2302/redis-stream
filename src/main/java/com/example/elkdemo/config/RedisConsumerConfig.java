package com.example.elkdemo.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class RedisConsumerConfig{

    @Value("${stream.key}")
    private String streamKey;

    @Value("${host.redis}")
    private String redisHost;

    @Value("${port.redis}")
    private int redisPort;

    private final StreamListener<String, ObjectRecord<String, String>> streamListener;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        return new JedisConnectionFactory(configuration);
    }


//    @Bean
//    public void createConsumerGroupIfNeeded() throws UnknownHostException {
//        String consumerGroupName = "thanhdz"; // Tên consumer group dựa trên biến String bạn truyền vào
//
//        try (Jedis jedis = (Jedis) jedisConnectionFactory().getConnection()) {
//            // Kiểm tra sự tồn tại của stream key
//            boolean streamKeyExists = jedis.exists(streamKey);
//
//            if (!streamKeyExists) {
//                // Nếu stream key chưa tồn tại, tạo stream key mới
//                Map<String, String> initialData = Map.of("message", "initial-data");
//                jedis.xadd(streamKey, StreamEntryID.NEW_ENTRY, initialData);
//                System.out.println("Created new stream key: " + streamKey);
//            }
//
//            // Kiểm tra sự tồn tại của consumer group
//            boolean consumerGroupExists = jedis.xinfoGroup(streamKey).stream()
//                    .anyMatch(groupInfo -> groupInfo.getName().equals(consumerGroupName));
//
//            if (!consumerGroupExists) {
//                // Nếu consumer group chưa tồn tại, tạo nó
//                jedis.xgroupCreate(streamKey.getBytes(), consumerGroupName.getBytes(), "0".getBytes(), true);
//                System.out.println("Created new consumer group: " + consumerGroupName);
//            }
//        }
//    }


    @Bean
    public Subscription subscription() throws UnknownHostException {

        var options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(String.class)
                .build();
        var listenerContainer = StreamMessageListenerContainer
                .create(jedisConnectionFactory(), options);

//        // Tên consumer group dựa trên biến String bạn truyền vào
//        String consumerGroupName = "thanhdz" ;
//
//        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
//            // Kiểm tra sự tồn tại của consumer group
//            boolean consumerGroupExists = jedis.xinfoGroup(streamKey).stream()
//                    .anyMatch(groupInfo -> groupInfo.getName().equals(consumerGroupName));
//
//            if (!consumerGroupExists) {
//                // Nếu consumer group chưa tồn tại, tạo nó
//                jedis.xgroupCreate(streamKey.getBytes(), consumerGroupName.getBytes(), "0".getBytes(), true);
//            }
//        }


        var subscription = listenerContainer.receiveAutoAck(
                Consumer.from(streamKey, InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                streamListener);
        listenerContainer.start();
        return subscription;
    }
}