package com.omprakash.orderservspringkt.consumer

import com.omprakash.orderservspringkt.config.CREATE_ORDER_TOPIC
import com.omprakash.orderservspringkt.config.GROUP_ID
import com.omprakash.orderservspringkt.dto.Events
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class EventConsumer {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = [CREATE_ORDER_TOPIC], groupId = GROUP_ID)
    fun createOrderEventListener(message: Events.CreateOrder) {
        logger.info("Message received: [$message]")
    }
}