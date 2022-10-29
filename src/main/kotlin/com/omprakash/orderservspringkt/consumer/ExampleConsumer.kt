package com.omprakash.orderservspringkt.consumer

import com.omprakash.orderservspringkt.config.CREATE_ORDER_TOPIC
import com.omprakash.orderservspringkt.config.GROUP_ID
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ExampleConsumer {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = [CREATE_ORDER_TOPIC], groupId = GROUP_ID)
    fun firstListener(message: String) {
        logger.info("Message received: [$message]")
    }
}