package com.alex.conduktor.takehome.service;

import com.alex.conduktor.takehome.beans.Person;
import com.alex.conduktor.takehome.config.KafkaConfiguration;
import com.alex.conduktor.takehome.constants.Constants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaService {

    @Autowired
    KafkaConfiguration kafkaConfig;

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    /**
     * Produce a message to a Kafka topic.
     * @param p
     */
    public void produce(Person p) {
        kafkaTemplate.send(Constants.TOPIC, p.getId(), p);
    }

    /**
     * Consume messages from a Kafka topic, and deserialize them into the given type.
     * @param offset
     * @param limit
     */
    public List<Person> consume(long offset, int limit) {
        ConsumerFactory<String, Person> factory = kafkaConfig.consumerFactory(limit);
        List<Person> people = new ArrayList<>();
        try (Consumer<String, Person> consumer = factory.createConsumer()) {
//            consumer.subscribe(List.of(Constants.TOPIC));
            List<TopicPartition> partitions = kafkaConfig.getPartitions();
            consumer.assign(partitions);
            for (TopicPartition partition : kafkaConfig.getPartitions()) {
                consumer.seek(partition, offset);
                ConsumerRecords<String, Person> records = consumer.poll(100);
                records.forEach(record -> {
                    System.out.println("Consumed record: " + record.value());
                    people.add(record.value());
                });
            }
        }
        return people;
    }
}
