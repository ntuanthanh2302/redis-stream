package com.example.elkdemo.repository;

import com.example.elkdemo.entity.UserElasticSearchEntity;
import com.example.elkdemo.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserElasticSearchRepository extends ElasticsearchRepository<UserElasticSearchEntity,Long> {
    List<UserElasticSearchEntity> findUserByName(String field);
}
