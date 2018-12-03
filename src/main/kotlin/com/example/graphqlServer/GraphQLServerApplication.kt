package com.example.graphqlServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphQLServerApplication

fun main(args: Array<String>) {
    runApplication<GraphQLServerApplication>(*args)
}
