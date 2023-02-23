package com.mongodb.controller;

import com.mongodb.collections.Person;
import com.mongodb.service.PersonService;
import io.swagger.models.auth.In;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
    @Autowired
    private  PersonService personService;


    @PostMapping("/save")
    public ResponseEntity<?> savePerson(@RequestBody() Person person) {
        Integer i = personService.savePerson(person);
        return ResponseEntity.ok(i);
    }
    @GetMapping()
    public List<Person> getPerson(@RequestParam int id){
       return personService.getPerson(id);
    }
    @GetMapping ("/age")
    public List<Person> getPersonByAge(@RequestParam String  minAge,@RequestParam String  maxAge){
       return personService.findPersonByAgeBetween(minAge,maxAge);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        personService.delete(id);
    }
    @GetMapping("/")
    public List<Person> getAllPersons(){
      return  personService.findAll();
    }
    @GetMapping("/oldestPerson")
    public  List<Document> getOldestPersonByCity(){
       return personService.getOldestPersonByCity();

    }
    @GetMapping("/getCount")
   public List<Document> getCountOfCities(){
       return personService.getCityCount();
    }
}