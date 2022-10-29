package com.omprakash.orderservspringkt.producer

import com.omprakash.orderservspringkt.config.CREATE_ORDER_TOPIC
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ExampleStringProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun sendStringMessage(message: String){
        kafkaTemplate.send(CREATE_ORDER_TOPIC,message)
    }
}