package com.example.elkdemo.repository;

import com.example.elkdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query(value = "SELECT * from elk.user",nativeQuery = true)
    List<UserEntity> GetAll();
}
