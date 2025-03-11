package com.udemy.webflux.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(

    @Id
    val id: String?,

    val name: String,

    @Indexed(unique = true)
    val email: String,

    val password: String

) {

}