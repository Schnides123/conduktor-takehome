package com.alex.conduktor.takehome.beans;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @JsonAlias("_id")
    private String id;
    private String name;
    private String dob;
    private Address address;
    private String telephone;
    private String[] pets;
    private double score;
    private String email;
    private String url;
    private String description;
    private boolean verified = false;
    private int salary = 0;
}