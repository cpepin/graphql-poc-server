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

    /*
     * Some fun with data-loaders here.  You need to expose a registry of loaders to the
     * GQL context, so that queries/mutations/resolves can use them.  In this case, I am
     * only creating a single data-loader, "customerDataLoader".
     *
     * In this case, data-loader keeps a map in memory that memoizes the REST
     * calls the customer client.  So if you have already filled in the customer data for a user
     * with `customerId: 1`, GraphQL will recognize it and provide the data from the in-memory
     * cache.
     */
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