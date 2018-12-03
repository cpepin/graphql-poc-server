package com.example.graphqlServer

import com.coxautodev.graphql.tools.GraphQLResolver
import com.example.graphqlServer.client.CustomerClient
import com.example.graphqlServer.resource.Customer
import com.example.graphqlServer.resource.User
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class UserResolver (
        private val customerClient: CustomerClient
) : GraphQLResolver<User> {
    fun customer(user: User, dfe: DataFetchingEnvironment) : CompletableFuture<Customer> {
        val dataLoader = dfe.getDataLoader<String, Customer>("customerDataLoader")

        return dataLoader.load(user.customerId)

        // customerClient.getCustomerById(user.customerId);
    }
}