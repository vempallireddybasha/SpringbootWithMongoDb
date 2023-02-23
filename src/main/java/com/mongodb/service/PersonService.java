package com.mongodb.service;

import com.mongodb.collections.Person;
import com.mongodb.repo.PersonRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private  PersonRepository personRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    public Integer savePerson(Person person) {
        return personRepository.save(person).getId();
    }

    public List<Person> getPerson(int id) {

        List<Person> person = personRepository.getPersonById(id);

           return person;
        }


    public List<Person> findPersonByAgeBetween(String minAge, String maxAge) {
        return personRepository.findPersonByAgeBetween(minAge,maxAge);
    }


    public void delete(Integer id) {
        personRepository.deleteById(id);
    }

    public List<Person> findAll() {
      return  personRepository.findAll();
    }

    public List<Document> getOldestPersonByCity() {
       // return personRepository.getOlderstPersonByCity();

        UnwindOperation unwindOperation= Aggregation.unwind("addresses");
        SortOperation sortOperation=Aggregation.sort(Sort.Direction.DESC,"age");
        GroupOperation groupOperation=Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestPerson");

        Aggregation aggregation
                =Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);
        List<Document> mappedResults = mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();
          return  mappedResults;

  }

    public List<Document> getCityCount() {
      //  UnwindOperation unwindOperation1=Aggregation.unwind("name");
        UnwindOperation unwindOperation=
                Aggregation.unwind("addresses");
        GroupOperation groupOperation=
                Aggregation.group("addresses.city","name","gender")
                        .count().as("cityCount");
        SortOperation sortOperation
                =Aggregation.sort(Sort.Direction.DESC,"cityCount");
         //GroupOperation groupOperation1=Aggregation.group("name");
        ProjectionOperation projectionOperation= Aggregation
                .project()
               // .andExpression("_id").as("city")
                .and("name").as("PersonName")
                .and("city").as("city")
                .and("gender").as("Gender")
                .andExclude("_id") //.andExpression("_id").as("name")
                .andExpression("cityCount").as("cityCount")
                ;


        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);
    return mongoTemplate.aggregate(aggregation,Person.class,Document.class).getMappedResults();
    }
}
