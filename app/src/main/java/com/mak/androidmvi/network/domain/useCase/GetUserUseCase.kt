package com.mak.androidmvi.network.domain.useCase

import com.mak.androidmvi.network.domain.model.User
import com.mak.androidmvi.network.domain.repository.UserRepository

class GetUserUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(id: String): Result<User> = repo.getUser(id)
}