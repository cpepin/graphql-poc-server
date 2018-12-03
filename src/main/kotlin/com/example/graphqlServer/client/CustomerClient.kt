package com.example.graphqlServer.client

import com.example.graphqlServer.resource.Customer
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class CustomerClient(
        private val restTemplate: RestTemplate
) {
    fun getCustomerById(id: String): Customer? {
        val request = RequestEntity<Any>(HttpMethod.GET, URI.create("http://localhost:8082/customers/$id"))
        val responseType = object : ParameterizedTypeReference<Customer>() {}
        val response = restTemplate.exchange(request, responseType)
        return response.body
    }
}