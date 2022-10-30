package com.omprakash.orderservspringkt.base

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName


abstract class ExternalContainerConfiguration {
    companion object {
        @Container
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:latest").apply {
            withDatabaseName("testDB")
            withUsername("test")
            withPassword("test")
        }

        @Container
        private val kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))



        @DynamicPropertySource
        @JvmStatic
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
            registry.add("spring.kafka.properties.bootstrap.servers", kafkaContainer::getBootstrapServers)
        }
    }
}