package com.alex.conduktor.takehome.constants;

public interface Constants {

    String TOPIC = "people";
    String BROKER_1 = "localhost:19092";
    String BROKER_2 = "localhost:29092";
    String BROKER_3 = "localhost:39092";
    String BROKERS = BROKER_1 + "," + BROKER_2 + "," + BROKER_3;
    String KAFKA_GROUP_ID = "example_group_id";
    String SECURITY_PROTOCOL = "security.protocol";
    String SASL_PLAINTEXT = "SASL_PLAINTEXT";
    String SASL_MECHANISM = "sasl.mechanism";
    String SCRAM_SHA_256 = "SCRAM-SHA-256";
    String SASL_JAAS_CONFIG = "sasl.jaas.config";
    String SASL_JAAS_CONFIG_VALUE = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"superuser\" password=\"secretpassword\";";
    String MAX_POLL_RECORDS = "max.poll.records";
}
