package com.example.graphqlServer

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.graphqlServer.client.UserClient
import org.springframework.stereotype.Component

@Component
class UserQueryResolver(
        private val userClient : UserClient
) : GraphQLQueryResolver {
    fun getAllUsers() = userClient.getAllUsers()
    fun getUserById(id: String) = userClient.getUserById(id)
}