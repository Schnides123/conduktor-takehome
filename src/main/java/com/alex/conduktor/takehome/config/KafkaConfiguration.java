package com.alex.conduktor.takehome.config;

import com.alex.conduktor.takehome.beans.Person;
import com.alex.conduktor.takehome.beans.PersonDeserializer;
import com.alex.conduktor.takehome.constants.Constants;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alex.conduktor.takehome.constants.Constants.BROKERS;

@EnableKafka
@Component
@Getter
public class KafkaConfiguration {

    List<TopicPartition> partitions = getTopicPartitions();

    public ConsumerFactory<String, Person> consumerFactory(int maxRecords)
    {
        // Creating a Map of string-object pairs
        Map<String, Object> config = new HashMap<>();

        // Adding the Configuration
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKERS);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.KAFKA_GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PersonDeserializer.class);
        config.put(Constants.MAX_POLL_RECORDS, maxRecords);

        // Don't do this in prod pls
        config.put(Constants.SECURITY_PROTOCOL, Constants.SASL_PLAINTEXT);
        config.put(Constants.SASL_MECHANISM, Constants.SCRAM_SHA_256);
        config.put(Constants.SASL_JAAS_CONFIG, Constants.SASL_JAAS_CONFIG_VALUE);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    private List<TopicPartition> getTopicPartitions() {
        return List.of(
                new TopicPartition(Constants.TOPIC, 0),
                new TopicPartition(Constants.TOPIC, 1),
                new TopicPartition(Constants.TOPIC, 2)
        );
    }
}
