package com.sns.project.chat.kafka;

import com.sns.project.chat.kafka.dto.request.KafkaChatEnterRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

  @Bean
  public JsonDeserializer<KafkaChatEnterRequest> jsonDeserializer() {
    return new JsonDeserializer<>(KafkaChatEnterRequest.class);
  }
  
  @Bean
  public ConsumerFactory<String, KafkaChatEnterRequest> consumerFactory() {
    JsonDeserializer<KafkaChatEnterRequest> deserializer = jsonDeserializer();
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(false);

    ErrorHandlingDeserializer<KafkaChatEnterRequest> errorHandlingDeserializer =
        new ErrorHandlingDeserializer<>(deserializer);

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "chat-enter-group");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    return new DefaultKafkaConsumerFactory<>(
        props,
        new StringDeserializer(),
        errorHandlingDeserializer
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, KafkaChatEnterRequest> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, KafkaChatEnterRequest> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setCommonErrorHandler(new DefaultErrorHandler((record, exception) -> {
      // Silence error
    }));
    return factory;
  }
}