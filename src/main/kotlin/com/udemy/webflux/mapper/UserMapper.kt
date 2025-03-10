package com.udemy.webflux.mapper

import com.udemy.webflux.entity.User
import com.udemy.webflux.model.request.UserRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
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
}