package com.example.elkdemo.entity;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "dtstest")
public class LogMessageEntity {
    @Id
//    @Field(type = FieldType.Long, name = "id")
    String id;

//    @Field(type = FieldType.Date, name = "@timestamp")
//    Timestamp timestamp;

    @Field(type = FieldType.Text, name = "@version")
    String version;

    @Field(type = FieldType.Auto, name = "_index")
    String index;

    @Field(type = FieldType.Text, name = "_score")
    Integer score;

    @Field(type = FieldType.Text, name = "message")
    String message;

    @Field(type = FieldType.Text, name = "_id")
    String feildId;

    @Field(type = FieldType.Text, name = "host.name")
    String hostName;
//
//    @Field(type = FieldType.Text, name = "name")
//    String name;
//
//    @Field(type = FieldType.Text, name = "age")
//    Integer age;
}
