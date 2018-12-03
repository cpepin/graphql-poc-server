package com.example.graphqlServer.resource

data class User(
        val id: String,
        val name: String,
        var customerId: String
)