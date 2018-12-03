package com.example.graphqlServer.client

import com.example.graphqlServer.resource.User
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class UserClient(
        private val restTemplate: RestTemplate
) {
    fun getAllUsers(): List<User>? {
        val request = RequestEntity<Any>(HttpMethod.GET, URI.create("http://localhost:8081/users"))
        val responseType = object: ParameterizedTypeReference<List<User>>() {}
        val response = restTemplate.exchange(request, responseType)
        return response.body
    }

    fun getUserById(id: String): User? {
        val request = RequestEntity<Any>(HttpMethod.GET, URI.create("http://localhost:8081/users/$id"))
        val responseType = object : ParameterizedTypeReference<User>() {}
        val response = restTemplate.exchange(request, responseType)
        return response.body
    }

    fun createUser(name: String, customerId: String): User? {
        val request = RequestEntity
                .post(URI.create("http://localhost:8081/users"))
                .body(User(name = name, customerId = customerId, id = ""))
        val responseType = object : ParameterizedTypeReference<User>() {}
        val response = restTemplate.exchange(request, responseType)
        return response.body
    }
}