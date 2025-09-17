package com.mak.androidmvi.network.domain.repository

import com.mak.androidmvi.network.domain.model.User

interface UserRepository {
    suspend fun getUser(id: String): Result<User>
}