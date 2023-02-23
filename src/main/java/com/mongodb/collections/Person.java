package com.mongodb.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import java.util.List;

@Document
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    private Integer id;
    private String name;
    private String age;
    private String gender;
    private List<Address> addresses;

}