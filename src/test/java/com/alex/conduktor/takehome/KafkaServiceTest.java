package com.alex.conduktor.takehome;

import com.alex.conduktor.takehome.beans.Address;
import com.alex.conduktor.takehome.beans.Person;
import com.alex.conduktor.takehome.service.KafkaService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class KafkaServiceTest {

    @Autowired
    private KafkaService kafkaService;

    @Test
    void testProducer() {
        Person p = new Person("368YCC2THQH1HEAQ",
        "Kiana Yoo",
        "2021-05-31",
        new Address("2745 Shaftsbury Circle", "Slough", "LS67 1ID"),
        "+39-3380-033-155",
         new String[]{"Sadie", "Rosie"},
        3.7,
        "palma14@hotmail.com",
        "http://earth.com",
        "strips rt administrators composer mumbai warranty tribunal excited halo costumes surgery series spare ticket monitored whose criminal screens enrollment range",
        true,
        31900);


        kafkaService.produce(p);

        List<Person> people = kafkaService.consume(0, 1);

        assert(people.size() > 0);
    }
}
