package com.omprakash.orderservspringkt.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfig(private val kafkaProperties: KafkaProperties) {

    @Bean
    fun createOrderTopic(): NewTopic {
        return TopicBuilder.name(CREATE_ORDER_TOPIC).build()
    }
}