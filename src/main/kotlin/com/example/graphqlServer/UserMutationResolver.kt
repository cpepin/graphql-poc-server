package com.example.graphqlServer

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.graphqlServer.client.UserClient
import org.springframework.stereotype.Component

@Component
class UserMutationResolver(
        private val userClient: UserClient
) : GraphQLMutationResolver {
    fun createUser(name: String, customerId: String) = userClient.createUser(name, customerId)
}