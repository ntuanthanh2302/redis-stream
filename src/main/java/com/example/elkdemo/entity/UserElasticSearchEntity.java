package com.example.elkdemo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "dtstest")
public class UserElasticSearchEntity {

    @Id
//    @Field(type = FieldType.Long, name = "id")
    Long id;

    @Field(type = FieldType.Text, name = "@timestamp")
    String timestamp;

    @Field(type = FieldType.Text, name = "@version")
    String version;

    @Field(type = FieldType.Text, name = "_index")
    String index;

    @Field(type = FieldType.Text, name = "_score")
    String score;

    @Field(type = FieldType.Auto, name = "_id")
    String feildId;

    @Field(type = FieldType.Text, name = "name")
    String name;

    @Field(type = FieldType.Text, name = "age")
    Integer age;
}
