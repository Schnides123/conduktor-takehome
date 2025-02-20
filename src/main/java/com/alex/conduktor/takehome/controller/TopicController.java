package com.alex.conduktor.takehome.controller;

import com.alex.conduktor.takehome.beans.Person;
import com.alex.conduktor.takehome.service.JsonFileService;
import com.alex.conduktor.takehome.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class TopicController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private JsonFileService jsonFileService;

    @GetMapping("/topic/{offset}")
    public ResponseEntity<List<Person>> getPeople(@PathVariable long offset, @RequestParam(defaultValue="10") int limit) {
        return ResponseEntity.ok(kafkaService.consume(offset, limit));
    }

    @PostMapping("/load-people")
    public ResponseEntity<Void> loadPeople() {
        try {
            jsonFileService.readPeopleJsonFile("data/random-people-data.json")
                    .forEach(person -> kafkaService.produce(person));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
