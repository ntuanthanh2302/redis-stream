package com.example.elkdemo.service;

import com.example.elkdemo.entity.LogMessageEntity;
import com.example.elkdemo.entity.UserElasticSearchEntity;
import com.example.elkdemo.entity.UserEntity;
import com.example.elkdemo.repository.UserElasticSearchRepository;
import com.example.elkdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserElasticSearchRepository userElasticSearchRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    public List<UserEntity> GetAllUser() throws IOException {
        List<UserEntity> userEntities = userRepository.GetAll();
//        List<UserElasticSearchEntity> userElasticSearchEntities = new ArrayList<>();
//
//        for (UserEntity userEntity :userEntities ) {
//            UserElasticSearchEntity elasticSearch = new UserElasticSearchEntity();
////            elasticSearch.setId(userEntity.getId());
////            elasticSearch.setName(userEntity.getName());
////            elasticSearch.setAge(userEntity.getAge());
////
////            userElasticSearchEntities.add(elasticSearch);
//
//        }


//        userElasticSearchRepository.saveAll(userElasticSearchEntities);
        return userEntities;
    }

    public List<UserElasticSearchEntity> findByFieldName(String fieldName) {
        return userElasticSearchRepository.findUserByName(fieldName);
    }

    public List<UserElasticSearchEntity> searchByContent(String name,long id) {
        Criteria criteria = new Criteria("name").is(name)
                .and(new Criteria("id").is(id));

        SearchHits<UserElasticSearchEntity> searchHits = elasticsearchOperations.search(
                new CriteriaQuery(criteria), UserElasticSearchEntity.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<LogMessageEntity> logMessageGet(String searchTerm){

        Criteria criteria = new Criteria("message").contains(searchTerm);

        SearchHits<LogMessageEntity> searchHits = elasticsearchOperations.search(
                new CriteriaQuery(criteria), LogMessageEntity.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
