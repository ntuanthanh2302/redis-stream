package com.example.elkdemo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
public class UserEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "age")
    Integer age;

}
