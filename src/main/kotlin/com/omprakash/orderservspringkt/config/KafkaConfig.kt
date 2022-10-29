package com.omprakash.orderservspringkt.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.config.SaslConfigs
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import javax.security.sasl.Sasl

@Configuration
class KafkaConfig(private val kafkaProperties: KafkaProperties) {

//    @Bean
//    fun admin(): KafkaAdmin {
//        return KafkaAdmin(
//            mapOf(
//                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
//                SaslConfigs.SASL_MECHANISM to "PLAIN",
//                SaslConfigs.SASL_JAAS_CONFIG to kafkaProperties.jaas
//            )
//        )
//    }

    @Bean
    fun createOrderTopic(): NewTopic {
        return TopicBuilder.name(CREATE_ORDER_TOPIC).build()
    }
}