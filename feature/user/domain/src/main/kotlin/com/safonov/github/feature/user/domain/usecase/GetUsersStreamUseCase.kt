package com.safonov.github.feature.user.domain.usecase

import com.safonov.github.feature.user.domain.model.User
import com.safonov.github.feature.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersStreamUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getUsersStream()
    }
}