package com.example.elkdemo.service;

import com.example.elkdemo.entity.UserEntity;
import com.example.elkdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumerService implements StreamListener<String, ObjectRecord<String,String>> {

    private final UserRepository userRepository;

    private static Logger log =  LoggerFactory.getLogger(ConsumerService.class);
    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        log.info(message.getValue());
        test(message.getValue());
    }

    public List<UserEntity> test(String message){
//        userRepository.GetAll();
        return userRepository.GetAll();
    }
}
