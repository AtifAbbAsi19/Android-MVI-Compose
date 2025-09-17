package com.mak.androidmvi.network.data.remote.dto

import com.mak.androidmvi.network.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String
) {
    fun toDomain(): User = User(id = id, name = name, email = email)
}