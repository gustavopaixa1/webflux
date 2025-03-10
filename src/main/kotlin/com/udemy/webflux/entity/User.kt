package com.udemy.webflux.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(

    @Id
    private val id: String?,

    private val name: String,

    @Indexed(unique = true)
    private val email: String,

    private val password: String

) {

}