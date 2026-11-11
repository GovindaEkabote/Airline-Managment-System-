
package com.airline.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    // Reads Kafka broker address from application.properties.
    //
    // Example:
    // spring.kafka.bootstrap-servers=localhost:9092
    //
    // Docker:
    // spring.kafka.bootstrap-servers=kafka:9092
    //
    // This is the address of the Kafka broker to which
    // the producer will connect.
    @Value("localhost:9094")
    private String bootstrapServers;


    // Creates and configures the Kafka ProducerFactory.
    //
    // ProducerFactory is responsible for creating
    // Kafka Producer instances.
    //
    // String = Message key type
    // Object = Message value type
    //
    // Example:
    // Key   -> "booking-101"
    // Value -> BookingResponse object
    @Bean
    public ProducerFactory<String, Object> producerFactory() {

        // Stores Kafka producer configuration properties
        // in key-value format.
        Map<String, Object> config = new HashMap<>();

        // Specifies the Kafka broker address.
        // Producer uses this address to connect to Kafka.
        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );

        // Specifies the serializer for message keys.
        //
        // Converts String keys into byte arrays
        // because Kafka stores messages as bytes.
        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        // Specifies the serializer for message values.
        //
        // Converts Java objects into JSON format
        // before publishing them to Kafka.
        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JacksonJsonSerializer.class
        );

        // Creates and returns a ProducerFactory
        // using the above configuration.
        return new DefaultKafkaProducerFactory<>(config);
    }


    // Creates KafkaTemplate as a Spring Bean.
    //
    // KafkaTemplate is the main class used by application
    // code to send messages to Kafka topics.
    //
    // Instead of manually creating Kafka Producer objects,
    // we inject KafkaTemplate wherever required.
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {

        // Uses the ProducerFactory to create KafkaTemplate.
        return new KafkaTemplate<>(producerFactory());
    }
}
