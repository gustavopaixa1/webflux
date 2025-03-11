package com.udemy.webflux.mapper

import com.udemy.webflux.entity.User
import com.udemy.webflux.model.request.UserRequest
import com.udemy.webflux.model.response.UserResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
import org.mapstruct.NullValueCheckStrategy.ALWAYS


@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = IGNORE,
    nullValueCheckStrategy = ALWAYS
)
interface UserMapper {

    @Mapping(target = "id", ignore = true)
    fun toEntity(request: UserRequest): User

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.name", source = "request.name")
    @Mapping(target = "user.email", source = "request.email")
    @Mapping(target = "user.password", source = "request.password")
    fun toEntity(request: UserRequest, @MappingTarget user: User): User

    fun toResponse(entity: User): UserResponse
}