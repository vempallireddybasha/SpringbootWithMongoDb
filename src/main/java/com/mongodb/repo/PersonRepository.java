package com.mongodb.repo;

import com.mongodb.collections.Person;
import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository  extends MongoRepository<Person,Integer> {
   List<Person> getPersonById(int id);

   @Query(value = "{'age' : { $gt: ?0,$lt: ?1}}")
    List<Person> findPersonByAgeBetween(String minAge, String maxAge);

   // List<Document> getOlderstPersonByCity();

    // List<Person> findByAgeBetween(Integer minAge,Integer maxAge);
}
