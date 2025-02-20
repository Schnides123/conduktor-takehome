package com.alex.conduktor.takehome.service;

import com.alex.conduktor.takehome.beans.Person;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonFileService {
    public List<Person> readPeopleJsonFile(String filename) throws IOException {
        List<Person> people = new ArrayList<>();
        File f = new File(filename);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(f);
        JsonNode ctRoot = node.get("ctRoot");
        if(ctRoot != null && ctRoot.isArray()) {
            for(final JsonNode objNode : ctRoot) {
                people.add(mapper.convertValue(objNode, Person.class));
            }
        }
        return people;
    }
}
