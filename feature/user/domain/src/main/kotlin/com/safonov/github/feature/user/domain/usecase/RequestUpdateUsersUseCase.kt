package com.safonov.github.feature.user.domain.usecase

import com.safonov.github.feature.user.domain.repository.UserRepository

class RequestUpdateUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.requestUsersUpdate()
    }
}