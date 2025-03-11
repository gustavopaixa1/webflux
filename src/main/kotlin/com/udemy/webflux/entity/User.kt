package com.udemy.webflux.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(

    @Id
    var id: String?,

    var name: String,

    @Indexed(unique = true)
    var email: String,

    var password: String

) {

}