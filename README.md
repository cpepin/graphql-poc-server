# GraphQL POC

A Kotlin based Spring Boot GraphQL server that pulls data from two basic REST APIs.

## Getting started

There are two other code bases you will need to clone other than this one.
* User REST API - https://github.com/cpepin/graphql-poc-user-service
* Customer REST API - https://github.com/cpepin/graphql-poc-customer-service

All three servers (including this one) can be started using `mvn spring-boot:run`

## Trying it out

The GraphQL server exposes an in-browser IDE for exploring GraphQL called graphiql.  You can
access this by going to http://localhost:8080/graphiql in your browser.  This web app exposes
docs, which will show you all of the available queries/mutations/types.

## What this covers
* Types
* Queries
* Mutations
* data-loader (IMPORTANT FOR PERFORMANCE)

## Things I could (and probably should) add
* Input types
* Subscriptions (which I'm not sure are implemented in Java yet)
* Testing