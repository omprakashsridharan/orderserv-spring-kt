 package com.omprakash.orderservspringkt

import com.omprakash.orderservspringkt.producer.CreateOrderEventProducer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class OrderservSpringKtApplication

fun main(args: Array<String>) {
	runApplication<OrderservSpringKtApplication>(*args)
}

@Component
class CommandExample(private val createOrderEventProducer: CreateOrderEventProducer) : CommandLineRunner {
	override fun run(vararg args: String?) {
//		exampleStringProducer.sendStringMessage("test message")
	}
}