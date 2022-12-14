package com.omprakash.orderservspringkt.producer

import com.omprakash.orderservspringkt.config.CREATE_ORDER_TOPIC
import com.omprakash.orderservspringkt.dto.Events
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CreateOrderEventProducer(private val createOrderTemplate: KafkaTemplate<String, Events.CreateOrder>) {

    fun sendCreateOrderEvent(createOrderEvent: Events.CreateOrder): Result<Boolean> {
        return try {
            val result = createOrderTemplate.send(CREATE_ORDER_TOPIC, createOrderEvent).get()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}