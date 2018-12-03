package com.example.graphqlServer

import com.example.graphqlServer.client.CustomerClient
import com.example.graphqlServer.resource.Customer
import graphql.servlet.GraphQLContext
import graphql.servlet.GraphQLContextBuilder
import org.dataloader.BatchLoader
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.websocket.Session
import javax.websocket.server.HandshakeRequest
import java.util.concurrent.CompletableFuture


@Component
class CustomGraphQLContext(private val customerClient: CustomerClient) : GraphQLContextBuilder {

    override fun build(req: HttpServletRequest, response: HttpServletResponse): GraphQLContext {
        val context = GraphQLContext(req, response)
        context.setDataLoaderRegistry(buildDataLoaderRegistry())

        return context
    }

    override fun build(): GraphQLContext {
        val context = GraphQLContext()
        context.setDataLoaderRegistry(buildDataLoaderRegistry())

        return context
    }

    override fun build(session: Session, request: HandshakeRequest): GraphQLContext {
        val context = GraphQLContext(session, request)
        context.setDataLoaderRegistry(buildDataLoaderRegistry())

        return context
    }

    private fun buildDataLoaderRegistry(): DataLoaderRegistry {
        val dataLoaderRegistry = DataLoaderRegistry()
        val customerBatchLoader = BatchLoader<String, Customer> { customerIds ->
            CompletableFuture.supplyAsync<List<Customer?>> {
                customerIds
                        .map { id -> customerClient.getCustomerById(id) }
            }
        }
        dataLoaderRegistry.register("customerDataLoader", DataLoader.newDataLoader(customerBatchLoader))
        return dataLoaderRegistry
    }
}