package com.safonov.github.feature.user.domain.usecase

import com.safonov.github.feature.user.domain.model.User
import com.safonov.github.feature.user.domain.repository.UserRepository

class GetUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> {
        return repository.getUsers()
    }
}